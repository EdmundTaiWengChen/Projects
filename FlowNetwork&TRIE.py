"""
FIT2004 Assignment 2 S1/2023
Python 3.9

Name: Edmund Tai Weng Chen
Student ID: 30884098
Email: etai0005@student.monash.edu
"""


# remember, no codes outside

# ==================== Some Extra Code??? ====================

class NetworkGraph:
    """
        This is a weighted graph with a set of Vertex objects. It has an add_vertex method for adding a vertex to the graph and an add_edge method for adding an edge between two vertices.
        Moreover, it has the get vertex method that get the vertex in the list of vetices by using the index given index.
        The Edge and Vertex classes, respectively, represent an edge and a vertex in a weighted graph. To represent its edges, the Vertex class has a list of Edge objects.
    """

    def __init__(self):
        self.vertices = []  # list of vertices

    def add_vertex(self, id):
        # add a vertex
        self.vertices.append(Vertex(id))

    def add_edge(self, src, dest, capacity):
        # add an edge
        src_vertex = self.vertices[src]
        dest_vertex = self.vertices[dest]
        src_vertex.edges.append(Edge(src_vertex, dest_vertex, capacity))

    def get_vertex(self, idex):
        return self.vertices[idex]


class Vertex:
    """
        The Vertex class has a list of Edge objects and some other attributes.
    """

    def __init__(self, id):
        self.id = id  # id of the vertex can set target or source
        self.edges = []  # list of edges
        self.source = False
        self.target = False
        self.previousEdge = None  # use for backtrack
        self.capacity = float('inf')  # the centre itself
        self.currentFlow = 0  # this is used how many flow in the centre now.


class Edge:
    """
        The edge class has 3 parameter which are u, v and w .
        The u represent the source where it come from, the v is where it can go and w is how much it weighted (capacity).
        There also other attributes like flow it means the total flowing in the edge itself currently.
    """

    def __init__(self, src, dest, capacity):
        self.src = src  # source
        self.dest = dest  # destination vertex of the edge
        self.capacity = capacity  # weight of the edge
        self.flow = 0
        self.reverse_edge = False #check whether the edge is reverse or not.


# ==================== Q1 Code ====================


def maxThroughput(connections, maxIn, maxOut, origin, targets):
    """
        Function description:
                The algorithm of the whole maxThroughput is started by creating a networkgraph(actually residual graph) with an origin and a target.
                There is a list of data centres (vertices) and list of connections (edges) where each data centres has a capacity(min(MaxIn, MaxOut).
                Moreover, the sending of data between data centre is connected with a cable where it has information where the cable is connected to and from with a
                capacity flow as well. Thus, we can find the optimal flow from data centre to connection cable that won't exceed its capacity by taking a path finding the smallest
                remaining flow (capacity - flow) of connections cable and data centre. For example, source data center has 3000 max in and 2000 max out with a connection cable of 1500 capacity flow
                toward another tower centre (target) that has 1000 max in and 5000 max out. If we take the number 1000 and add the data centre with connection cable by 1000 will not exceed
                the capacity of the data centre and cable connected to. In the network graph a super target is created where all the data centre in the targets list will be connected to the super target.
                After adding all the necessary edge to the vertex then a network graph is fully created. By finding the min flow for each path and update the path with the min flow. In the end,
                we can find the max flow of the network graph.

        Approach description:
                The graph design approach is where each data centre represent a vertex and in the vertex itself has a list of edge show where the data centre can connect to with which data centre.
                Since the targets can be a list super target is always created no matter the target has one element in the list or not. After finish creating the vertex then edges will be added based
                on the connections list. Once all the edge(connections) are added, the edges that connected to the super target will get added as well. Once all finish executing, a network graph(residual graph)
                is created. Now using Breath first search (BFS) to go all the edges from source to target that create a path. With the path found, will then use to find min remaining flow (capacity - flow) across
                the path. Once the min remaining path is find update all the data centre and edges that is in the path. If the following edge(connections) is added then a reverse edge is created as well. Repeat the process by using
                BFS again to find a path until no augmented path can be found then the loop terminate and return a max flow based on the network graph created.

        Input:
            connections: a list connections of the direct communication channels between the data centres.
            maxIn: The maximum amount of incoming data that data centre i can process per second.
            maxOut: The sum of the throughputs across all incoming communication channels to data centre i.
            origin: The source data centre.
            targets: Data centres that are deemed appropriate locations for the backup data to be stored.

        Return:
            answerq1: The maximum flow based on the network graph created.

        Time complexity:
             Best: O(|D| · |C|^2) , |D| data centres and |C| communication channels
             Worst: O(|D| · |C|^2) , |D| data centres and |C| communication channels
        Space complexity:
            Input: O(|D| · |C|^2) , |D| data centres and |C| communication channels
            Aux: O(|D| · |C|^2) , |D| data centres and |C| communication channels
        """
    answerq1 = 0

    networkGraph = NetworkGraph()
    numberOfCentre = len(maxIn)  # number of centre with a super target
    # create vertex and a super target
    for i in range(numberOfCentre+1):
        networkGraph.add_vertex(i)
        currentVertex = networkGraph.get_vertex(i)
        if i is origin:
            currentVertex.source = True
            currentVertex.capacity = maxOut[i]
        elif i is numberOfCentre:  # the last node is the super target doesnt have anything just a reference point
            currentVertex.target = True
        else:
            current_idx = i
            isTarget = False
            for j in targets:
                if current_idx is j:
                    isTarget = True

            if isTarget:
                current_capacity = maxIn[i]
            else:
                current_capacity = maxIn[i]
                if maxOut[i] < current_capacity:
                    current_capacity = maxOut[i]  # max out is smaller

            currentVertex.capacity = current_capacity

    # create edge
    for j in range(len(connections)):
        src, dest, capacity = connections[j][0], connections[j][1], connections[j][2]
        networkGraph.add_edge(src, dest, capacity)

    # create edge for super target
    for k in targets:
        # access the vertices
        src, dest, capacity = k, numberOfCentre, maxIn[k]
        networkGraph.add_edge(src, dest, capacity)

    while bfs(networkGraph, networkGraph.get_vertex(origin), networkGraph.get_vertex(numberOfCentre)): #true a path is form
        min_flow = 0
        supertarget_vertex = networkGraph.get_vertex(numberOfCentre) #supertarget

        #loop backward find the minimum capacity of vertex and edge
        while supertarget_vertex.id is not networkGraph.get_vertex(origin).id:

            backTrackEdge = supertarget_vertex.previousEdge
            theVertexIComeFrom = backTrackEdge.src

            min_edge = backTrackEdge.capacity - backTrackEdge.flow
            min_vertex = theVertexIComeFrom.capacity - theVertexIComeFrom.currentFlow

            if min_flow == 0:
                min_flow = min_edge
            if min_vertex < min_flow:
                min_flow = min_vertex
            elif min_edge < min_flow:
                min_flow = min_edge
            # Go to next vertex
            supertarget_vertex = theVertexIComeFrom
        answerq1 += min_flow #Add path flow to overall flow


        supertarget_vertex_2 = networkGraph.get_vertex(numberOfCentre)  # supertarget
        while supertarget_vertex_2.id is not networkGraph.get_vertex(origin).id:
            backTrackEdge1 = supertarget_vertex_2.previousEdge
            theVertexIComeFrom1 = backTrackEdge1.src
            # update all the following vertices and edges
            # now I know the optimal flow is required to flow for this path

            #find the edge inside edges
            for i in theVertexIComeFrom1.edges:
                if i.src.id is backTrackEdge1.src.id and i.dest.id is backTrackEdge1.dest.id:
                    foundEdge = i

            foundEdge.flow += min_flow
            theVertexIComeFrom1.currentFlow += min_flow

            #reverse edge
            if theVertexIComeFrom1.id is not networkGraph.get_vertex(origin).id or backTrackEdge1.dest.id is not networkGraph.get_vertex(numberOfCentre):
                if backTrackEdge1.reverse_edge is False:
                    src, dest, capacity = backTrackEdge1.dest.id, backTrackEdge1.src.id,  min_flow
                    networkGraph.add_edge(src, dest, capacity)
                    previousAddedEdge = backTrackEdge1.dest.edges[-1]
                    previousAddedEdge.reverse_edge = True

            # Go to next vertex
            supertarget_vertex_2 = theVertexIComeFrom1

    return answerq1


def bfs(network_graph, source, target):
    """
        Function description:
            This is a breath first search (BFS) algorithm where it starts from source data centre and using the edge in the edges list to investigate all
            nodes(data centre). If the node already visited then it will not visit the node again. It will repeat the process till the data centre is the super target
            then it wil return a path back to method for further modification.

        Input:
            network_graph: the network graph
            source: the source data centre
            target: the target data centre

        Return:
            False: No more augmented path
            True: There is a path

        Time complexity:
            Best: O(|D| · |C|^2) , |D| data centres and |C| communication channels
            Worst: O(|D| · |C|^2) , |D| data centres and |C| communication channels
        Space complexity:
            Input: O(|D| · |C|^2) , |D| data centres and |C| communication channels
            Aux: O(V) , queue.
    """
    numberOfNodes = len(network_graph.vertices)+1
    visited = [False] * numberOfNodes #make sure the path is forward always
    queue = [source]  #store the vertices that has edge to travel to from origin
    visited[source.id] = True

    while queue:
        u = queue.pop(0) #pop the left most vertex
        for edge in u.edges: #get every edges
            remaining_edge = edge.capacity - edge.flow
            remaining_vertex = edge.dest.capacity - edge.dest.currentFlow
            if remaining_edge > 0 and remaining_vertex > 0 and visited[edge.dest.id] is False:  # this edge can flow to v
                # store the edge in the vertex and append it in the queue and update visited[id] is true
                queue.append(edge.dest)
                visited[edge.dest.id] = True
                edge.dest.previousEdge = edge
                #reach target found a path
                if edge.dest.id is target.id:
                    return True

    #no more path
    return False

# ==================== Q2 Code ====================

class CatsTrie:
    """
        This is a Trie tree data structure where it takes a list of words in the sentences list. Each words as a character will be store as a node in the Trie.
        After building finish the trie, we can transverse down a path of the trie to get the substring of the word.
        The TrieNode, which includes data, children, and isEnd, is defined first. The node's data is the character it stores.
        The isEnd property indicates whether this node is the last node in a word. The children are the links to the nodes that contain the next letter in the word.
    """

    def __init__(self, sentences):
        """
        A root is created.

        Precondition: sentences cannot be empty list and in the sentences cannot have empty string.
        Postcondition: the highest frequency and lexicographically smaller string will be prompt out.

        Input:
            sentences: a list of word. Each word is a combination of character from a to z small alphabet.
        Return:
            None

        Time complexity:
            Best: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
            Worst: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
        Space complexity:
            Input: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
            Aux: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
        """
        self.root = TrieNode()
        self.insert(sentences)


    def insert(self, sentences):
        """
        Function description:
             Find a word from the sentences list and insert the word into the trie, Iteration.

        Input:
            sentences:  a list of word. Each word is a combination of character from a to z small alphabet.

        Return:
            None

        Time complexity:
            Best: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
            Worst: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
        Space complexity:
            Input: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
            Aux: O(NM) , N is the number of sentence in sentences and M is the number of characters in the longest sentence.
        """

        for word in sentences:
            currentNode = self.root  # starting point
            for ch in word:
                idx = ord(ch) - 97  # 'a' start from 0.
                # if path not exist
                if currentNode.childrens[idx] is None:
                    # check whether the data is none or not
                    if currentNode.data is None:
                        currentNode.childrens[idx] = TrieNode(ch)
                    else:
                        # get the previous data and concatenate (exp: a -> aa)
                        currentNode.childrens[idx] = TrieNode(currentNode.data + ch)
                # update the node.
                currentNode = currentNode.childrens[idx]
            # now the word is completed form
            currentNode.deadEnd = True
            # update the frequency
            currentNode.frequency += 1

    def autoComplete(self, prompt):
        """
            Function description:
                Given the prompt by the user. We will then use the length of character in the prompt loop through every node and using generate word method to revursively
                generate all the possible word. After a word is generated, compare the previous word in terms of highest frequency and lower lexicographically smaller string.
                Then it will return a word with highest frequency and lower lexicographically smaller string.

            Approach description:
                Using the Trie tree graph created from root node traverse to another node based on the character from prompt. Once the finish looping through the prompt we can then
                start looping through all possible word can be form using the generates word method that run recursively.

            Input:
                prompt: a string with characters in the set of [a...z].
            Return:
                answerq2: a word with highest frequency and lower lexicographically smaller string.

            Time complexity:
                 Best: O(X) , X is the length of the prompt no such word exist.
                 Worst: O(XY) , X is the length of the prompt and Y is the length of the most frequent sentence in sentences that begins with the prompt.
            Space complexity:
                Input: O(X) , X is the length of the prompt no such word exist.
                Aux: O(n), number of nodes include (Prefix and branches)

            """
        answerq2 = None
        node = self.root  # starting point
        for ch in prompt:
            idx = ord(ch) - 97  # 'a' start from 0.
            node = node.childrens[idx]  # also update the node
            # here i fast check whether the given prompt is in the sentence or not
            if node is None:  # example prompt('cda') but the sentence only has word start from 'a' or only second character with 'd'
                return None
        # now i know the prompt value can generate a word.
        answerq2 = self.generateWord(node, answerq2)

        return answerq2[1]

    def generateWord(self, node, answerq2):
        """
        Function description:
            A recursive function where it generates all possible word from the latest node based on the prompt character.

        Input:
            node: the current node
            answerq2: None
        Return:
            answerq2: tuple (frequency , words) where a word with highest frequency and lower lexicographically smaller string.

        Time complexity: 
            Best: O(n), number of nodes include (Prefix and branches)
            Worst: O(n), number of nodes include (Prefix and branches)
        Space complexity: 
            Input: O(n), number of nodes include (Prefix and branches)
            Aux: O(n), number of nodes include (Prefix and branches)
        """
        if node is None:
            return answerq2
        # condition frequency will not be 0, either 1 or more than that.
        if node.deadEnd:  # all possible word can be formed based on the prompt
            # check if the answerq2 is none meaning frist word found
            if answerq2 is None:
                answerq2 = (node.frequency, node.data)  # store important information
            # check frequency same
            elif answerq2[0] == node.frequency:
                # if frequency same check for lexicographically smaller
                if node.data < answerq2[1]:
                    answerq2 = (node.frequency, node.data)
            # check the word found who frequency bigger
            elif node.frequency > answerq2[0]:
                answerq2 = (node.frequency, node.data)
        for child in node.childrens:
            answerq2 = self.generateWord(child, answerq2)

        return answerq2


class TrieNode:
    """
    Function description:
        The node of Trie that is a list.

    Input:
        None
    Return:
        None

    Time complexity:
        Best: O(1)
        Worst: O(1)
    Space complexity:
        Input: O(1)
        Aux: O(1)

    """
    def __init__(self, data=None):
        self.childrens = [None] * 26  # only small alphabet no space or speical character. 26 is a constant
        self.deadEnd = False  # deadEnd true mean is a word.
        self.frequency = 0  # number of words appear.
        self.data = data  # words form for each traversal


# # ==================== Main Functions ====================
#
# if __name__ == "__main__":


# Example
# Nothing here
# Please do not submit any test cases...


