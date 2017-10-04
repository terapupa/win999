package guidezup.win999.sys.DAO;

public interface PlayerDao {

    public Player createPlayer(String firstName, String lastName, String email) throws OperationException;

    public Player updatePlayer(String id, String firstName, String lastName, String email) throws OperationException;

    public Player retrievePlayer(String id) throws OperationException;

    public Player deactivatePlayer(String id) throws OperationException;

    /**
     * Get Instant account verification token.
     * @param id - player ID
     * @return - Instant account verification token
     * @throws OperationException - operation exception
     */
    public String getIavToken(String id) throws OperationException;

}
