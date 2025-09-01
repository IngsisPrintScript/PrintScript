package repositories;

public interface CodeRepositoryInterface {
    Boolean hasMoreCode();
    String nextChunkOfCode();
}
