import java.util.Comparator;

public class PostComparator implements Comparator<Post> {
    @Override
    public int compare(Post p1, Post p2) {
        // Descending by likes
        if (p1.getLike() != p2.getLike()) {
            return Integer.compare(p2.getLike(), p1.getLike());
        }
        // Descending lexicographical order by postId
        return p2.getPostId().compareTo(p1.getPostId());
    }
}