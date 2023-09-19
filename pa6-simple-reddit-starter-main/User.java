
/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * this class is represents a user in our simplified Reddit program
 * it include username, karma, all posts of this user, all upvoted 
 * and downvoted posts of this user
 */
import java.util.ArrayList;

/**
 * this class is represents a user in our simplified Reddit program
 * it include username, karma, all posts of this user, all upvoted
 * and downvoted posts of this user
 */
public class User {
    // calculated as
    // (the total number of upvotes) minus
    // (the total number of downvotes)
    // This value can be negative.
    private int karma;
    // this User's username
    private String username;
    // A list of posts this User has made,
    // including original posts and comments.
    private ArrayList<Post> posts;
    // A list of other users' posts that
    // this User upvoted.
    private ArrayList<Post> upvoted;
    // A list of other users' posts that
    // this User downvoted.
    private ArrayList<Post> downvoted;

    /**
     * The constructor for a User.
     * Initialize username to the parameter value
     * and all ArrayLists to empty ArrayLists.
     * Initialize karma to 0.
     * 
     * @param username user's name
     */
    public User(String username) {
        this.username = username;
        posts = new ArrayList<>();
        upvoted = new ArrayList<>();
        downvoted = new ArrayList<>();
        karma = 0;
    }

    /**
     * Add a Post to end of this User's posts.
     * 
     * @param post the post to be add
     */
    public void addPost(Post post) {
        // check if the post is null
        if (post != null) {
            posts.add(post);
            updateKarma();
        }

    }

    /**
     * Update this User's karma by going through
     * the user's posts and summing up
     * upvoteCount-downvoteCount for each post.
     */
    public void updateKarma() {
        int totalUpvote = 0;
        int totalDownvote = 0;

        // loop posts
        for (Post post : posts) {
            // summing up upvotecount
            totalUpvote += post.getUpvoteCount();
            // summing up downvotecount
            totalDownvote += post.getDownvoteCount();
        }
        karma = totalUpvote - totalDownvote;

    }

    /**
     * Return the current value of karma
     * 
     * @return value of karma
     */
    public int getKarma() {
        return karma;
    }

    /**
     * Add post to upvoted, update post's upvoteCount.
     * Update the author's karma value.
     * 
     * @param post the post may need to add to upvoted list
     */
    public void upvote(Post post) {
        // check if post is null
        if (post == null) {
            return;
        }
        // check if user has already been upvoted
        if (upvoted.contains(post)) {
            return;
        }

        // check if user is the author of this post
        if (post.getAuthor().equals(this)) {
            return;
        }

        boolean isIncrement = true;
        // check if post is in downvoved
        if (downvoted.contains(post)) {
            // remove post from downvoted
            downvoted.remove(post);
            isIncrement = false;
            // update downvotecount
            post.updateDownvoteCount(isIncrement);
        }
        // add post to upvoted
        upvoted.add(post);
        isIncrement = true;
        // update upvotecount
        post.updateUpvoteCount(isIncrement);
        post.getAuthor().updateKarma();

    }

    /**
     * Add post to downvoted, update post's downvoteCount.
     * Update the author's karma value.
     * 
     * @param post the post may need to add to downvoted list
     */
    public void downvote(Post post) {
        // check if post is null
        if (post == null) {
            return;
        }

        // check if user has already been downvoted
        if (downvoted.contains(post)) {
            return;
        }

        // check if user is the author of this post
        if (post.getAuthor().equals(this)) {
            return;
        }

        boolean isIncrement = true;
        // check if post is in upvoved
        if (upvoted.contains(post)) {
            // remove post from upvoted
            upvoted.remove(post);
            isIncrement = false;
            // update upvotecount
            post.updateUpvoteCount(isIncrement);
        }

        // add to downvoted
        downvoted.add(post);
        isIncrement = true;
        // update downvoted
        post.updateDownvoteCount(isIncrement);
        post.getAuthor().updateKarma();
    }

    /**
     * get the top original post determined by the
     * greatest (upvoteCount-downvoteCount) value
     * 
     * @return the top original post determined
     *         by the greatest (upvoteCount-downvoteCount)
     *         value
     */
    public Post getTopPost() {
        int greatest = 0;
        int count = 0;
        Post tPost = null;
        // loop posts
        for (Post post : posts) {
            // check if post is a orginal post
            if (post.getTitle() != null) {
                // get (upvoteCount-downvoteCount) value
                count = post.getUpvoteCount() - post.getDownvoteCount();
                // check if the count is greatest
                if (count > greatest) {
                    greatest = count;
                    tPost = post;
                }
            }
        }
        return tPost;
    }

    /**
     * get the top comment determined by
     * the greatest (upvoteCount-downvoteCount) value.
     * 
     * @return the top comment determined by
     *         the greatest (upvoteCount-downvoteCount)
     *         value.
     */
    public Post getTopComment() {
        int greatest = 0;
        int count = 0;
        Post tComment = null;
        // loop posts
        for (Post comment : posts) {
            // check if post is a comment
            if (comment.getTitle() == null) {
                // get (upvoteCount-downvoteCount) value
                count = comment.getUpvoteCount() - comment.getDownvoteCount();
                // check if the count is greates
                if (count > greatest) {
                    greatest = count;
                    tComment = comment;
                }
            }
        }
        return tComment;
    }

    /**
     * get the list of posts (original posts and comments)
     * made by the User.
     * 
     * @return the list of posts
     */
    public ArrayList<Post> getPosts() {
        return posts;

    }

    /**
     * Return a String representation of this User.
     * 
     * @return String representation of this User.
     */
    public String toString() {
        String format = "u/%s Karma: %d";

        return String.format(format, username, karma);

    }

}
