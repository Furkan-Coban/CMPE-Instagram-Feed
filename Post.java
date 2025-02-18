
public class Post  {
    private String postId;
    private String authorId;
    private String content;
    private int like;
    private MyHashSet<String> likedBy;

    public Post(String postId, String authorId, String content) {
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.like = 0;
        this.likedBy = new MyHashSet<>();
    }


    public boolean likeClicked(String userId){
        if(likedBy.contains(userId)){
            likedBy.remove(userId); //If the user already liked the post, unlike the post.
            like--;
            return false; // false indicates unlike case.
        }
        else{
            likedBy.add(userId);
            like++;
            return true; // true indicates like case.
        }
    }


    public String getPostId() {
        return postId;
    }


    public String getAuthorId() {
        return authorId;
    }


    public int getLike() {
        return like;
    }

}
