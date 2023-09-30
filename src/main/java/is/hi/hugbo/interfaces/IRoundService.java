package is.hi.hugbo.interfaces;

import is.hi.hugbo.model.Round;

public interface IRoundService {
  /**
   * # Creates a new round and saves it to the database
   * 
   * @param username - The username of the user who created the round,
   *                 taken from
   *                 the session
   * @param courseId - The id of the course the round was played on
   * @param int[]    holes - The scores for each hole
   * @return Newly created round
   */
  public Round save(String username, Long courseId, int[] holes);

  /**
   * Takes a round and updates its holes
   * 
   * @param round - The round to update
   * @param holes - The updated holes
   * @return The updated round
   */
  public Round update(Round round, int[] holes);
}
