package is.hi.hugbo.interfaces;

import is.hi.hugbo.model.Round;

public interface IRoundService {
  /**
   * Searches the database for round with given id
   *
   * @param id - The id of the round to find
   * @return The round with given id or null if not found
   */
  public Round findById(long id);

  /**
   * Creates a new round and saves it to the database
   * 
   * @param courseId - The id of the course the round was played on
   * @param username - The username of the user who created the round,
   *                 taken from
   *                 the session
   * @param int[]    holes - The scores for each hole
   * @return Newly created round
   */
  public Round save(long courseId, long userId, int[] holes);

  /**
   * Takes a round and updates its holes
   * 
   * @param round - The round to update
   * @param holes - The updated holes
   * @return The updated round
   */
  public Round update(Round round, int[] holes);

  /**
   * Method to delete a round from the database
   * 
   * @param round - The round to delete
   */
  public void delete(Round round);
}
