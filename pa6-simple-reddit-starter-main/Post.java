/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * this class is a post in simplified version of Reddit
 * where users can create, upvote, and downvote posts.
 * you can either create a post or comment on someone's 
 * existing post. 
 */

import java.util.ArrayList;

/**
 * this class is a post in simplified version of Reddit
 * where users can create, upvote, and downvote posts.
 * you can either create a post or comment on someone's
 * existing post.
 */
public class Post {

    // the title of a Reddit post. If this post is a comment,
    // then title should be null. title should be non-null if
    // this post is an original post.
    private String title;
    // the content of a Reddit post. If this post is a comment,
    // content is the comment a User made.
    private String content;
    // the original post or comment this Post is replying to.
    // If this Post is an original post, replyTo should be null.
    // replyTo should be non-null if this post is a comment.
    private Post replyTo;
    // the author of this Post.
    private User author;
    // the number of upvotes of this Post.
    private int upvoteCount;
    // the number of downvotes of this Post.
    private int downvoteCount;

    /**
     * The constructor for initializing an original post.
     * Set upvoteCount to 1 and Set downvoteCount to 0.
     * 
     * @param title   the title of the post
     * @param content the content of the post
     * @param author  the author of the post
     */
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
        this.replyTo = null;
    }

    /**
     * The constructor for initializing a comment.
     * Set upvoteCount to 1. Set downvoteCount to 0.
     * 
     * @param content the content of the comment
     * @param replyTo the original post or comment
     * @param author  the author of the comment
     */
    public Post(String content, Post replyTo, User author) {
        this.content = content;
        this.replyTo = replyTo;
        this.author = author;
        this.upvoteCount = 1;
        this.downvoteCount = 0;
        this.title = null;
    }

    /**
     * get title of the post
     * 
     * @return the title of this Post.
     */
    public String getTitle() {
        return title;
    }

    /**
     * get the Post of that this Post is replying to.
     * 
     * @return the Post of that this Post is replying to.
     */
    public Post getReplyTo() {
        return replyTo;
    }

    /**
     * get the author of the post
     * 
     * @return the author of this Post.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * get the the number of upvote for the post
     * 
     * @return the number of upvotes of this Post.
     */
    public int getUpvoteCount() {
        return upvoteCount;
    }

    /**
     * get the the number of downvotes for the post
     * 
     * @return the number of downvotes of this Post.
     */
    public int getDownvoteCount() {
        return downvoteCount;
    }

    /**
     * Increment upvoteCount by 1 if isIncrement is true,
     * otherwise decrement upvoteCount by 1.
     * 
     * @param isIncrement if need increment
     */
    public void updateUpvoteCount(boolean isIncrement) {
        if (isIncrement) {
            upvoteCount++;
        } else {
            upvoteCount--;
        }
    }

    /**
     * Increment downvoteCount by 1 if isIncrement is true,
     * otherwise decrement downvoteCount by 1.
     * 
     * @param isIncrement if need increment
     */
    public void updateDownvoteCount(boolean isIncrement) {
        if (isIncrement) {
            downvoteCount++;
        } else {
            downvoteCount--;
        }
    }

    /**
     * get a list of posts in the current thread,
     * starting with the original post of this post
     * and ending with this post.
     * 
     * @return Return a list of posts in the current thread
     */
    public ArrayList<Post> getThread() {
        // a list to store all post
        ArrayList<Post> posts = new ArrayList<>();
        // flag to indicate if find the original post
        boolean isOriginal = false;
        // add this post to the list
        posts.add(this);

        Post comment = this;
        // check if this is the original post
        if (comment.title != null) {
            return posts;
        } else {
            while (!isOriginal) {
                // get the post that this post reply to
                comment = comment.getReplyTo();
                // check if comment have title
                if (comment.getTitle() == null) {
                    // add comment to the list infront
                    posts.add(0, comment);
                } else {
                    posts.add(0, comment);
                    // set isOriginal flag is true
                    isOriginal = true;
                }
            }
            return posts;
        }
    }

    /**
     * get a String representation of this Post.
     * 
     * @return a String representation of this Post.
     */
    public String toString() {
        String TO_STRING_POST_FORMAT = "[%d|%d]\t%s\n\t%s";
        String TO_STRING_COMMENT_FORMAT = "[%d|%d]\t%s";
        if (this.title != null) {
            return String.format(
                    TO_STRING_POST_FORMAT,
                    upvoteCount,
                    downvoteCount,
                    title,
                    content);
        } else {
            return String.format(
                    TO_STRING_COMMENT_FORMAT,
                    upvoteCount,
                    downvoteCount,
                    content);
        }

    }
}
