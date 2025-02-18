import java.util.ArrayList;
public class SystemManager {
    private MyHashMap<String,User> allUsers;
    private MyHashMap<String,Post> allPosts;

    public SystemManager(){
        this.allUsers = new MyHashMap<>();
        this.allPosts = new MyHashMap<>();
    }

    public String createUser(String userId){
        if(allUsers.containsKey(userId)){
            return "Some error occurred in create_user.";
        }
        allUsers.put(userId,new User(userId));
        return "Created user with Id " + userId + ".";
    }

    public String followUser(String followerId, String toFollowId){
        if(followerId.equals(toFollowId)){
            return "Some error occurred in follow_user."; // Prevent self-following.
        }
        User follower = allUsers.get(followerId);
        User toFollow = allUsers.get(toFollowId);
        if(follower==null||toFollow==null){
            return "Some error occurred in follow_user.";
        }
        if(toFollow.isFollowing(followerId)){
            return "Some error occurred in follow_user.";
        }

        follower.follow(toFollowId);
        toFollow.addFollower(followerId); // Related properties of both users are updated.
        return  followerId + " followed " + toFollowId + ".";
    }

    public String unfollowUser(String followerId, String toUnfollowId){
        User follower = allUsers.get(followerId);
        User toUnfollow = allUsers.get(toUnfollowId);

        if(follower==null||toUnfollow==null){
            return "Some error occurred in unfollow_user.";
        }
        if(!toUnfollow.isFollowing(followerId)){
            return "Some error occurred in unfollow_user.";
        }
        follower.unfollow(toUnfollowId);
        toUnfollow.removeFollower(followerId); // Related properties of both users are updated.
        return followerId + " unfollowed " + toUnfollowId + ".";
    }


    public String createPost(String userId, String postId, String content){

        User user = allUsers.get(userId);
        if(user==null){
            return "Some error occurred in create_post.";
        }
        if(allPosts.containsKey(postId)){
            return "Some error occurred in create_post.";
        }
        Post post = new Post(postId,userId,content);
        allPosts.put(postId,post);
        user.addPost(post);
        return userId + " created a post with Id " + postId + ".";
    }

    public String seePost(String userId, String postId){
        User user = allUsers.get(userId);
        Post post = allPosts.get(postId);

        if(user==null||post==null){
            return "Some error occurred in see_post.";
        }
        if(!user.hasSeenPost(postId)){
            user.markPostAsSeen(postId); // If the post is not already seen, it is marked as seen.
        }
        return userId + " saw " + postId + ".";
    }

    public String seeAllPosts(String viewerId, String viewedId){
        User viewer = allUsers.get(viewerId);
        User viewed = allUsers.get(viewedId);

        if(viewer==null || viewed==null){
            return "Some error occurred in see_all_posts_from_user.";
        }
        for(Post post:viewed.getAllPosts()){
            viewer.markPostAsSeen(post.getPostId());
        }
        return viewerId + " saw all posts of " + viewedId + ".";
    }

    public String toggleLike(String userId,String postId){
        User user = allUsers.get(userId);
        Post post = allPosts.get(postId);
        if(user==null||post==null){
            return "Some error occurred in toggle_like.";
        }

        boolean liked = post.likeClicked(userId); // If it is already liked, it returns false, otherwise true.

        user.markPostAsSeen(postId);
        if(liked){
            return userId + " liked " + postId + ".";
        }
        else{
            return userId + " unliked " + postId + ".";
        }

    }
    // Returns the suitable posts for the user.(unseen posts from followers)
    public MyPriorityQueue<Post> feedHelper(User user){
        ArrayList<Post> eligiblePosts = new ArrayList<>();
        for(String followedId:user.getFollowing()){
            User followedUser = allUsers.get(followedId);
            if(followedUser!=null){
                for(Post post:followedUser.getAllPosts()){
                    if(!user.hasSeenPost(post.getPostId())){
                        eligiblePosts.add(post);
                    }
                }
            }
        }
        MyPriorityQueue<Post> maxHeap = new MyPriorityQueue<>(new PostComparator());
        maxHeap.buildHeap(eligiblePosts); // Posts are added to maxHeap with buildHeap function to ensure O(n) time complexity.
        return maxHeap;
    }
    public String generateFeed(String userId, int num){
        User user = allUsers.get(userId);
        if(user==null){
            return "Some error occurred in generate_feed.";
        }
        MyPriorityQueue<Post> maxHeap = feedHelper(user);
        StringBuilder feed = new StringBuilder("Feed for " + userId + ":\n");
        int count = 0;
        while (!maxHeap.isEmpty()&&count<num){
            Post post = maxHeap.poll(); //Retrieve the highest-priority post
            feed.append("Post ID: ").append(post.getPostId())
                .append(", Author: ").append(post.getAuthorId())
                .append(", Likes: ").append(post.getLike()).append("\n");
            count++;
        }

        if(count<num){ //Checking whether there are enough suitable posts or not.
            feed.append("No more posts available for ").append(userId).append(".\n");
        }
        return feed.toString().trim();
    }
    public String scrollThroughFeed(String userId, int num, int[] likes){
        User user = allUsers.get(userId);
        if(user==null){
            return "Some error occurred in scroll_through_feed.";
        }

        StringBuilder result = new StringBuilder(userId + " is scrolling through feed:\n");
        MyPriorityQueue<Post> feed = feedHelper(user); // By using feedHelper feed will store the eligible posts.

        int count = 0;
        while(!feed.isEmpty()&& count<likes.length){
            Post post = feed.poll();
            boolean liked = (likes[count]==1); //It is true if the like button should be clicked, otherwise false.

            user.markPostAsSeen(post.getPostId());

            if(liked){
                post.likeClicked(userId);
                result.append(userId).append(" saw ").append(post.getPostId()).append(" while scrolling and clicked the like button.\n");
            }
            else{
                result.append(userId).append(" saw ").append(post.getPostId()).append(" while scrolling.\n");
            }
            count++;
        }
        if(count<num){
            result.append("No more posts in feed.");
        }
        return result.toString().trim();
    }
    public String sortPosts(String userId){
        User user = allUsers.get(userId);
        if(user==null){
            return "Some error occurred in sort_posts.";
        }
        if(user.getAllPosts().isEmpty()){
            return "No posts from " + userId + ".";
        }
        ArrayList<Post> posts = user.getAllPosts();
        MyPriorityQueue<Post> temp = new MyPriorityQueue<>(new PostComparator());
        temp.buildHeap(posts); //All the posts added to a maxHeap
        StringBuilder result = new StringBuilder("Sorting " + userId + "'s posts:\n");
        while(!temp.isEmpty()){
            Post post = temp.poll(); //Posts are sorted by taking the most priority post repeatedly,
            result.append(post.getPostId()).append(", Likes: ").append(post.getLike()).append("\n");
        }
        return result.toString().trim();
    }

}
