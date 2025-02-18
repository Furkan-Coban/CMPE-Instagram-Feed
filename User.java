import java.util.ArrayList;

public class User {
    private final String  userId;
    private MyHashSet<String> followers;
    private MyHashSet<String> following;
    private ArrayList<String> followingList;
    private ArrayList<Post> posts;
    private MyHashSet<String> seenPosts;

    public User(String userId) {
        this.userId = userId;
        this.followers = new MyHashSet<>();
        this.following = new MyHashSet<>();
        this.posts = new ArrayList<>();
        this.seenPosts = new MyHashSet<>();
        this.followingList = new ArrayList<>();
    }
    public void addPost(Post post) {
        posts.add(post);
    }

    public ArrayList<String> getFollowing() {
        return followingList; // To use buildHeap function, ArrayList of followingUsers is returned.
    }

    // following users are stored in two different data structure to achieve minimum time complexity.
    public boolean follow(String otherUserId) {
        followingList.add(otherUserId);
        return following.add(otherUserId);
    }

    public boolean unfollow(String otherUserId) {
        followingList.remove(otherUserId);
        return following.remove(otherUserId);
    }

    public boolean addFollower(String followerId) {
        return followers.add(followerId);
    }

    public boolean isFollowing(String userId){
        return followers.contains(userId);
    } // To check if the user is follower, using the HashSet of followers is more suitable.

    public boolean removeFollower(String followerId) {
        return followers.remove(followerId);
    }

    public ArrayList<Post> getAllPosts() {
        return posts;
    }

    public void markPostAsSeen(String postId) {
        seenPosts.add(postId);
    }

    public boolean hasSeenPost(String postId) {
        return seenPosts.contains(postId);
    }

}
