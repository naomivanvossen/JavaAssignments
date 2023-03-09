public class Hello {

   /* This program will print 'Hello' followed by
    * the startup arguments you provide.
    *  You can compile it by using the following command:
    *    'javac Hello.java'
    *  You can run it by using the following command:
    *    'java Hello world'
    */

   public static void main(String []args) {
      System.out.print("Hello ");
      for(String arg : args){
         System.out.print(arg+" ");
      }
      System.out.print("\n");
   }
}
