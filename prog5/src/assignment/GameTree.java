package assignment;

/**
 * Helper class to efficiently search through the game baord
 */
public class GameTree {
    CharacterNode head;


    public GameTree(CharacterNode head, char[][] board) {
        this.head = head;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                //buildTree(head);
            }
        }
    }


//    private void buildTree(CharacterNode current) {
//        for (int i = 0; i < board.length; i++) {
//            for (int j = 0; j < board.length; j++) {
//                buildTree(head);
//            }
//        }
//    }

    private class CharacterNode {
        char val;
        //each node has an array of 26 nodes (letters in alphabet) it points to
        CharacterNode[] nodes;

        CharacterNode(char c){
            val = c;
            //nodes store the 26 possible letters that could connect to the current node and also an arbitary character
            //in the 27th index that denotes the end of a word
            nodes = new CharacterNode[27];
        }

        /**
         * Used to set add a node to the current node
         * @param c
         */
        private CharacterNode setNode(char c) {
            //since dictionary provides lower case words we can determine the index we should
            //place a word by subtracting the value of c by the ASCII value of 'a'
            if (nodes[c-97] == null)
                nodes[c-97] = new CharacterNode(c);
            return nodes[c-97];
        }

        private CharacterNode getNode(char c){
            return nodes[c-97];
        }
    }
}
