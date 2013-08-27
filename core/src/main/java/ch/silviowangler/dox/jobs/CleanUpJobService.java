package ch.silviowangler.dox.jobs;

/**
 * @author Silvio Wangler
 * @since 0.3
 */
public interface CleanUpJobService {

    void collectFileSizeOnDocumentsThatHaveNotBeenAssignedYet();
}
