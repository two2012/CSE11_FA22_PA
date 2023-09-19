/*
 * Name: Wen Guo 
 * Email: w5guo@ucsd.edu
 * PID: A17630856
 * Sources used: write-up
 * 
 * this class is a test class for User and Post class
 */

public class Tester {

    public static void main(String[] args) {
        User u1 = new User("student1");
        User u2 = new User("student2");
        User u3 = new User("student3");

        Post p1 = new Post("title-1", "content-1", u1);
        Post p2 = new Post("title-2", "content-2", u2);
        Post p3 = new Post("title-3", "content-3", u3);

        Post c1 = new Post("comment-1", p1, u1);
        Post c2 = new Post("comment-2", p2, u2);
        Post c3 = new Post("comment-3", p3, u3);
        
        Post c11 = new Post("comment-11", p2, u1);
        Post c22 = new Post("comment-22", p3, u2);
        Post c33 = new Post("comment-33", p1, u3);

        u1.addPost(p1);
        u1.addPost(c1);
        u1.addPost(c11);

        u2.addPost(p2);
        u2.addPost(c2);
        u2.addPost(c22);

        u3.addPost(p3);
        u3.addPost(c3);
        u3.addPost(c33);

        u1.upvote(p2);
        u2.upvote(p2);
        u1.upvote(null);
        u1.upvote(p2);
        u3.upvote(p2);


        u1.downvote(p2);
        u1.downvote(null);

        System.out.println(p2.getDownvoteCount());
        System.out.println(p2.getUpvoteCount());
        System.out.println(p2.toString());

        System.out.println(c33.getThread());

        System.out.println(u2.getKarma());

        System.out.println(u2.getTopPost()); 
        System.out.println(u2.getTopComment());

        System.out.println(u3.getPosts());

        System.out.println(u2.toString());


        
        
    }

    

    
}
