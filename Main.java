import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
public class Main {

    public static void main(String[] args) {
        SystemManager systemManager = new SystemManager();
        String inputFile = args[0];
        String outputFile = args[1];
        StringBuilder outputBuffer = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile),64 * 1024)) {
            String line;
            while (((line = reader.readLine()) != null)){
                String[] parts = line.split(" ");
                String command = parts[0];
                switch (command) { // Using switch, decides which operation will be performed.
                    case "create_user":
                        String userId = parts[1];
                        String createUserResult = systemManager.createUser(userId);
                        outputBuffer.append(createUserResult).append("\n");
                        break;

                    case "follow_user":
                        String followerId = parts[1];
                        String toFollowId = parts[2];
                        String followResult = systemManager.followUser(followerId,toFollowId);
                        outputBuffer.append(followResult).append("\n");
                        break;

                    case "unfollow_user":
                        followerId = parts[1];
                        String toUnfollowId = parts[2];
                        String unfollowResult = systemManager.unfollowUser(followerId,toUnfollowId);
                        outputBuffer.append(unfollowResult).append("\n");
                        break;

                    case "create_post":
                        userId = parts[1];
                        String postId = parts[2];
                        String content = parts[3];
                        String createPostResult = systemManager.createPost(userId,postId,content);
                        outputBuffer.append(createPostResult).append("\n");
                        break;
                    case "see_post":
                        userId = parts[1];
                        postId = parts[2];
                        String seePostResult = systemManager.seePost(userId,postId);
                        outputBuffer.append(seePostResult).append("\n");
                        break;

                    case "see_all_posts_from_user":
                        String viewerId = parts[1];
                        String viewedId=  parts[2];
                        String seeAllPostsResult = systemManager.seeAllPosts(viewerId,viewedId);
                        outputBuffer.append(seeAllPostsResult).append("\n");
                        break;

                    case "toggle_like":
                        userId = parts[1];
                        postId = parts[2];
                        String toggleLikeResult = systemManager.toggleLike(userId,postId);
                        outputBuffer.append(toggleLikeResult).append("\n");
                        break;

                    case "generate_feed":
                        userId = parts[1];
                        int num = Integer.parseInt(parts[2]);
                        String generateFeedResult = systemManager.generateFeed(userId,num);
                        outputBuffer.append(generateFeedResult).append("\n");
                        break;

                    case "scroll_through_feed":
                        userId = parts[1];
                        num = Integer.parseInt(parts[2]);
                        int[] likes = new int[num];
                        for(int i=0;i<num;i++){
                            likes[i] = Integer.parseInt(parts[3+i]);
                        }
                        String scrollThroughFeedResult = systemManager.scrollThroughFeed(userId,num,likes);
                        outputBuffer.append(scrollThroughFeedResult).append("\n");
                        break;

                    case "sort_posts":
                        userId = parts[1];
                        String sortPostsResult = systemManager.sortPosts(userId);
                        outputBuffer.append(sortPostsResult).append("\n");
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile),64 * 1024)) {
            writer.write(outputBuffer.toString()); //All outputs are written in outputFile.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
