package cHash;

/**
 * @author linjunjie1103@gmail.com
 * Represent a node which should be mapped to a hash ring
 */
public interface Node {
    /**
     *
     * @return the key which will be used for hash mapping
     */
    String getKey();
}
