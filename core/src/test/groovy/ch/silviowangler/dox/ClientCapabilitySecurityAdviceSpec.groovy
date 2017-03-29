/*
 * Copyright 2012 - 2017 Silvio Wangler (silvio.wangler@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.silviowangler.dox

import ch.silviowangler.dox.api.DocumentClass
import ch.silviowangler.dox.api.DocumentReference
import ch.silviowangler.dox.api.PhysicalDocument
import ch.silviowangler.dox.api.security.UserService
import ch.silviowangler.dox.aspect.ClientCapabilitySecurityAdvice
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
        def userService = Mock(UserService)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository, userService)

        when: 'the advice gets called'
        def retVal = advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then:
        1 * userService.isLoggedIn() >> false

        and: 'the method calls would have the following arguments'
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
        def userService = Mock(UserService)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository, userService)

        when: 'the advice gets called'
        advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then:
        1 * userService.isLoggedIn() >> true

        and: 'the call should provoke an exception since the client field is not set'
        1 * pointCut.getArgs() >> [new DocumentReference(client: null)]

        and: 'Illegal argument exception is thrown'
        thrown(IllegalArgumentException)

        and: 'A user lookup is made'
        1 * doxUserRepository.findByUsername('username') >> new DoxUser(username: 'username', clients: [new Client(shortName: 'clientX')])
    }

    @Unroll
    void "Verify client capability mechanism with user authentication"() {

        given: 'A user in the security context'
        SecurityContextHolder.context.setAuthentication(new UsernamePasswordAuthenticationToken(new User('username', 'password', []), 'password'))

        and: 'further setup...'
        def pointCut = Mock(ProceedingJoinPoint)
        def doxUserRepository = Mock(DoxUserRepository)
        def userService = Mock(UserService)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository, userService)

        when: 'the advice gets called'
        def retVal = advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then:
        1 * userService.isLoggedIn() >> true

        and: 'the method calls would have the following arguments'
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
    void "#proceedCalls invocations and expect error message '#expectedMessage'"() {

        given: 'A user in the security context'
        SecurityContextHolder.context.setAuthentication(new UsernamePasswordAuthenticationToken(new User('username', 'password', []), 'password'))

        and: 'further setup...'
        def pointCut = Mock(ProceedingJoinPoint)
        def doxUserRepository = Mock(DoxUserRepository)
        def userService = Mock(UserService)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository, userService)

        when: 'the advice gets called'
        advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then:
        1 * userService.isLoggedIn() >> true

        and: 'the method calls would have the following arguments'
        1 * pointCut.getArgs() >> args

        and: 'the actual target is never called'
        proceedCalls * pointCut.proceed() >> pReturn

        and: 'a user lookup is made'
        1 * doxUserRepository.findByUsername('username') >> new DoxUser(username: 'username', clients: userClientAssignment)

        and: 'the call may proceed'
        def ex = thrown(AccessDeniedException)

        and:
        ex.message == expectedMessage

        where:
        args                                                                             | proceedCalls | pReturn                                                                            | userClientAssignment                                                                                   || expectedMessage
        [new DocumentClass(client: 'clientA')]                                           | 0            | null                                                                               | [new Client(shortName: 'clientB')]                                                                     || 'You have no access to client clientA'
        [new DocumentClass(client: 'clientX')]                                           | 0            | null                                                                               | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB')]                                   || 'You have no access to client clientX'
        [new DocumentClass(client: 'clientY')]                                           | 0            | null                                                                               | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB'), new Client(shortName: 'clientZ')] || 'You have no access to client clientY'
        [new DocumentClass(client: 'clientC'), new DocumentReference(client: 'clientD')] | 0            | null                                                                               | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB')]                                   || 'You have no access to clients clientC and clientD'
        [1L]                                                                             | 1            | new PhysicalDocument(client: 'clientC')                                            | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB')]                                   || 'You have no access to client clientC'
        [99L]                                                                            | 1            | [new PhysicalDocument(client: 'clientC'), new PhysicalDocument(client: 'clientZ')] | [new Client(shortName: 'clientA'), new Client(shortName: 'clientB')]                                   || 'You have no access to clients clientC and clientZ'
    }

    void "Very return values are checked too"() {
        given: 'A user in the security context'
        SecurityContextHolder.context.setAuthentication(new UsernamePasswordAuthenticationToken(new User('username', 'password', []), 'password'))

        and: 'further setup...'
        def pointCut = Mock(ProceedingJoinPoint)
        def doxUserRepository = Mock(DoxUserRepository)
        def userService = Mock(UserService)
        def advice = new ClientCapabilitySecurityAdvice(doxUserRepository, userService)

        when: 'the advice gets called'
        def retVal = advice.verifyUserCanPerformActionOnCurrentClient(pointCut)

        then:
        1 * userService.isLoggedIn() >> true

        and: 'the method calls would have the following arguments'
        1 * pointCut.getArgs() >> args

        and: 'the actual return value is set'
        1 * pointCut.proceed() >> returnValue

        and: 'a user lookup is made'
        1 * doxUserRepository.findByUsername('username') >> new DoxUser(username: 'username', clients: userClientAssignment)

        and: 'the call may proceed'
        retVal == returnValue

        where:
        args | userClientAssignment               || returnValue
        [1L] | [new Client(shortName: 'clientA')] || new PhysicalDocument(client: 'clientA')
    }
}
