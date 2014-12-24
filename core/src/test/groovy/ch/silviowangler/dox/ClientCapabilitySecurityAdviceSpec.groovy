package ch.silviowangler.dox

import ch.silviowangler.dox.api.DocumentClass
import ch.silviowangler.dox.api.DocumentReference
import ch.silviowangler.dox.domain.Client
import ch.silviowangler.dox.domain.security.DoxUser
import ch.silviowangler.dox.repository.security.DoxUserRepository
import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Silvio Wangler on 24.12.14.
 * @author Silvio Wangler
 * @since 0.4
 */
class ClientCapabilitySecurityAdviceSpec extends Specification {

    void "Verify client capability mechanism without user authentication"() {

        given:
        def pointCut = Mock(ProceedingJoinPoint)
        def doxUserRepository = Mock(DoxUserRepository)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository)

        when: 'the advice gets called'
        def retVal = advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then: 'the method calls would have the following arguments'
        1 * pointCut.getArgs() >> ['xxx', 1L, 44]

        and: 'the actual return value is set'
        1 * pointCut.proceed() >> 'Hello'

        and: 'No user lookup is made'
        0 * doxUserRepository.findByUsername(_)

        and: 'the call may proceed'
        retVal == 'Hello'
    }

    void "Verify client capability mechanism without user authentication and exceptions"() {

        given: 'A user in the security context'
        SecurityContextHolder.context.setAuthentication(new UsernamePasswordAuthenticationToken(new User('username', 'password', []), 'password'))

        def pointCut = Mock(ProceedingJoinPoint)
        def doxUserRepository = Mock(DoxUserRepository)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository)

        when: 'the advice gets called'
        advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then: 'the call should provoke an exception since the client field is not set'
        1 * pointCut.getArgs() >> [new DocumentReference(client: null)]

        and: 'Illegal argument exception is thrown'
        thrown(IllegalArgumentException)

        and: 'A user lookup is made'
        1 * doxUserRepository.findByUsername('username')
    }

    @Unroll
    void "Verify client capability mechanism with user authentication"() {

        given: 'A user in the security context'
        SecurityContextHolder.context.setAuthentication(new UsernamePasswordAuthenticationToken(new User('username', 'password', []), 'password'))

        and: 'further setup...'
        def pointCut = Mock(ProceedingJoinPoint)
        def doxUserRepository = Mock(DoxUserRepository)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository)

        when: 'the advice gets called'
        def retVal = advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then: 'the method calls would have the following arguments'
        1 * pointCut.getArgs() >> args

        and: 'the actual return value is set'
        1 * pointCut.proceed() >> returnValue

        and: 'a user lookup is made'
        1 * doxUserRepository.findByUsername('username') >> new DoxUser(username: 'username', clients: userClientAssignment)

        and: 'the call may proceed'
        retVal == returnValue

        where:
        args                                                                             | userClientAssignment                                                                                   || returnValue
        [new DocumentClass(client: 'clientA')]                                           | [new Client(shortName: 'clientA')]                                                                     || 'Hello'
        [new DocumentClass(client: 'clientB')]                                           | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB')]                                   || 'Yes I got through!'
        [new DocumentClass(client: 'clientZ')]                                           | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB'), new Client(shortName: 'clientZ')] || 'Yes I got through!'
        [new DocumentClass(client: 'clientZ'), new DocumentReference(client: 'clientA')] | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB'), new Client(shortName: 'clientZ')] || 'Yes I got through!'
    }

    @Unroll
    void "Verify client capability mechanism with user authentication and access denied"() {

        given: 'A user in the security context'
        SecurityContextHolder.context.setAuthentication(new UsernamePasswordAuthenticationToken(new User('username', 'password', []), 'password'))

        and: 'further setup...'
        def pointCut = Mock(ProceedingJoinPoint)
        def doxUserRepository = Mock(DoxUserRepository)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository)

        when: 'the advice gets called'
        advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then: 'the method calls would have the following arguments'
        1 * pointCut.getArgs() >> args

        and: 'the actual target is never called'
        0 * pointCut.proceed()

        and: 'a user lookup is made'
        1 * doxUserRepository.findByUsername('username') >> new DoxUser(username: 'username', clients: userClientAssignment)

        and: 'the call may proceed'
        def ex = thrown(AccessDeniedException)

        and:
        ex.message == expectedMessage

        where:
        args                                                                             | userClientAssignment                                                                                   || expectedMessage
        [new DocumentClass(client: 'clientA')]                                           | [new Client(shortName: 'clientB')]                                                                     || 'You have no access to client clientA'
        [new DocumentClass(client: 'clientX')]                                           | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB')]                                   || 'You have no access to client clientX'
        [new DocumentClass(client: 'clientY')]                                           | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB'), new Client(shortName: 'clientZ')] || 'You have no access to client clientY'
        [new DocumentClass(client: 'clientC'), new DocumentReference(client: 'clientD')] | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB')]                                   || 'You have no access to clients clientC and clientD'
    }
}
