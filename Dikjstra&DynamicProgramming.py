"""
FIT2004 Assignment 1 S1/2023
Python 3.10

Name: Edmund Tai Weng Chen
Student ID: 30884098
Email: etai0005@student.monash.edu
"""


class Graph:
    """
        This is a weighted graph with a set of Vertex objects. It has an add_vertex method for adding a vertex to the graph and an add_edge method for adding an edge between two vertices.
        The Edge and Vertex classes, respectively, represent an edge and a vertex in a weighted graph. To represent its edges, the Vertex class has a list of Edge objects.
    """

    def __init__(self):
        self.vertices = []  # list of vertices

    def add_vertex(self, id):
        #add a vertex
        self.vertices.append(Vertex(id))

    def add_edge(self, src, dest, weight):
        #add an edge
        src_vertex = self.vertices[src]
        dest_vertex = self.vertices[dest]
        src_vertex.edges.append(Edge(src_vertex, dest_vertex, weight))


class Edge:
    """
        The edge class has 3 attribute which are u, v and w .
        The u represent the source where it come from, the v is where it can go and w is how much it weighted (time taken).
    """
    def __init__(self, src, dest, weight):
        self.src = src  # source
        self.dest = dest  # destination vertex of the edge
        self.weight = weight  # weight of the edge


class Vertex:
    """
        The Vertex class has a list of Edge objects and some other attributes.
    """
    def __init__(self, id):
        self.id = id  # id of the vertex
        self.edges = []  # list of edges
        self.discovered = False
        self.visited = False
        self.previous = None
        self.distance = float('inf')


def optimalRoute(start, end, passengers, roads):
    """
    Function description: This function get the shortest path where it has the least time taken from start to end. It return a list of location where each index to index + 1 in the list represent a route.
                            In the roads list, there are all possible road available in that city. Using all the roads given to form a path that begin from my location (start location) to my destination (end location).
                            There is two possible lane here which are carpool lane and no carpool lane. The condition to use the carpool lane is in a car must be > 2 people. There are also a list of passengers in respective location.
                            We are allowed to pick them up as they are all going to the same destination as i am. Doing so, i can take the carpool lane which is guaranteed to take less time to drive finish the road compare
                            to non-carpool lane.

    Approach description: The algorithm here is using dijkstra algotihm to find all possible path with min heap that always return the smallest time taken. First, I created two weighted graph(normal graph) where one run dijkstra from
                            start to end whereas the other graph(reversed graph) run from end to start. The normal graph get a path where I didnt pick up passenger which means im using the non-carpool lane only.
                            As for the reversed graph I find the shortest path with carpool lane. The reason i start from end is because in the beginning of the road there is no customer thus i cant take start instead i take end.
                            That guaranteed to have passenger. After finish generating the two graph with dijkstra I then compare two graph where one is total time taken without carpool lane and the other one is a combination of normal graph
                            and reversed graph that shows that I have picked up customer. Using the two value i find and compare them i will choose the smaller value among them. After that i will need to do backtracking where I start from end
                            and get vertex.previos where i come from until to the start to form a path that return a list.

    Input:
        start: the source destination
        end: the destination
        passengers: A list of possible passenger at their respective location.
        roads: A list of tuple where inside a tuple has (u,v,w1,w2). u is start and v is end, w1 is non carpool lane time taken from start to end. w2 is carpool lane time taken from start to end.
    Return:
        result: a list of integer that represent the location.

    Time complexity:
        Best: O(R log L) , where R is the roads, L is the location.
        Worst: O(R log L) , where R is the roads, L is the location.
    Space complexity:
        Input: O(L+R), where L is the number of roads and R represent the roads list.
        Aux: O(L+R), where L is the number of roads and R represent the roads list.
    """
    numbers_of_vertex = find_the_largest_value_location(roads)
    normal_graph = Graph()

    # create all possible vertex
    for i in range(numbers_of_vertex+1):  #start from 0 and last number is inclusive
        normal_graph.add_vertex(i)

    # Add all the road in road list into the edge.
    for j in range(len(roads)):
        src, dest, weight = roads[j][0], roads[j][1], roads[j][2]
        normal_graph.add_edge(src, dest, weight)

    discovered_min_heap = min_heap()  # pirioty queue
    beginning_vertex = normal_graph.vertices[start]
    beginning_vertex.distance = 0
    discovered_min_heap.insert((beginning_vertex, beginning_vertex.distance))  # append(data, key)

    while discovered_min_heap.length > 0:
        # assume serve is same as pop(0)
        u = discovered_min_heap.serve()[0] #take a vertex
        u.visited = True  # means I have visit u, distance is finalized

        # perform edge relaxation on all adjacent vertices
        for edge in u.edges:
            v = edge.dest  # vertex that im going.
            if not v.discovered:  # means distance is still \inf
                v.discovered = True  # means I have discovered, adding it to queue
                v.distance = u.distance + edge.weight # vertex distance update from inf to a value.
                v.previous = u  # store this vertex,v previous is u.
                discovered_min_heap.insert((v, v.distance))  # store a vertex in the heap. Then compare them which is the smallest among all for min distance.
            # it is in heap, but not yet finalize
            elif not v.visited:  # discovered but not yet finalise meaning the vertices is still in heap.
                if u.distance + edge.weight < v.distance:
                    # update distance
                    v.distance = u.distance + edge.weight
                    v.previous = u
                    # update heap code it urself
                    discovered_min_heap.insert((v, v.distance))  # update vertex v in heap, with distance (smaller one) may move up heap.


    reversed_graph = Graph()
    # create all possible vertex
    for i in range(len(numbers_of_vertex)):
        reversed_graph.add_vertex(i)

    # Add all the road in road list into the edge.
    for j in range(len(roads)):
        src, dest, weight = roads[j][1], roads[j][0], roads[j][3]
        reversed_graph.add_edge(src, dest, weight)

    discovered_min_heap = min_heap()  # pirioty queue
    beginning_vertex = reversed_graph.vertices[end]
    beginning_vertex.distance = 0
    discovered_min_heap.append((beginning_vertex, beginning_vertex.distance))  # append(data, key)

    while discovered_min_heap.length > 0:
        # assume serve is same as pop(0)
        u = discovered_min_heap.serve()[0] #take a vertex
        u.visited = True  # means I have visit u, distance is finalized

        # perform edge relaxation on all adjacent vertices
        for edge in u.edges:
            v = edge.dest  # vertex that im going.
            if not v.discovered and v is not beginning_vertex:  # means distance is still \inf
                v.discovered = True  # means I have discovered, adding it to queue
                v.distance = u.distance + edge.weight  # vertex distance update from inf to a value.
                v.previous = u  # store this vertex,v previous is u.
                discovered_min_heap.insert((v, v.distance))  # store a vertex in the heap. Then compare them which is the smallest among all for min distance.
            # it is in heap, but not yet finalize
            elif not v.visited:  # discovered but not yet finalise meaning the vertices is still in heap.
                if u.distance + edge.weight < v.distance:
                    # update distance
                    v.distance = u.distance + edge.weight
                    v.previous = u
                    # update heap code it urself
                    discovered_min_heap.insert((v, v.distance))  # update vertex v in heap, with distance (smaller one) may move up heap.

    #no carpool lane act as result also
    no_carpool_lane_total_time_taken = normal_graph.vertices[end].distance

    #no carpool lane and carpool lane combined
    passenger = None
    result = []

    #here i wanna find whether did i pick up passenger.
    for i in passengers:
        combined_graph_total_time = normal_graph.vertices[i].distance + reversed_graph.vertices[i].distance
        if combined_graph_total_time < no_carpool_lane_total_time_taken:
            no_carpool_lane_total_time_taken = combined_graph_total_time
            passenger = i #here i know where i did pick up passenger.

    #the result example: 2 3 4 5 6 -> # # 4 # # it will first append the number in the list.
    if passenger == None:  # mean i didnt pick up any passenger.
        passenger = end
        while normal_graph.vertices[passenger].previous != None:
            result.insert(0, normal_graph.vertices[passenger].previous.id)

    else:
        while normal_graph.vertices[passenger].previous != None:
            result.insert(0, normal_graph.vertices[passenger].previous.id)
        while reversed_graph.vertices[passenger].previous != None:
            result.append(0, reversed_graph.vertices[passenger].previous.id)

    return result


#(tuple) vertex, distance distance
class min_heap:
    """
        A min heap class. Reference Pass week 6 video. It always rearranges the index value in the list where the smalles value is always at the top. A priority queues.
    """
    def __init__(self):
        self.array = [None]
        self.length = 0

    def insert(self, element):
        """
        Add an element to MinHeap's array
        Time complexity: O(log v), where v is the number of elements in the minimum heap
        """
        self.array.append(element)
        self.length += 1
        self.rise(self.length)

    def serve(self):
        self.swap(1, self.length)
        self.length -= 1
        self.sink(1)

        return self.array.pop()

    def swap(self, x, y):
        """
        Swap two number's position in the minimum heap
        Time Complexity: O(1)
        """
        self.array[x], self.array[y] = self.array[y], self.array[x]

    def rise(self, element):
        """
        Adjust the position of the number accordingly
        Time complexity: O(log v), where v is the number of elements in the minimum heap.
        """
        parent = element // 2
        while parent >= 1:
            if self.array[parent][1] > self.array[element][1]:
                self.swap(parent, element)
                element = parent
                parent = element // 2
            else:
                break

    def sink(self, element):
        child = 2*element
        while child <= self.length:
            if child < self.length and self.array[child+1][1] < self.array[child][1]:
                child += 1
            if self.array[element][1] > self.array[child][1]:
                self.swap(element, child)


def find_the_largest_value_location(input_list):
    largest_value = 0
    for i in range(len(input_list)):
        temp1 = input_list[i][0]  # start
        temp2 = input_list[i][1]  # end
        if temp1 > largest_value:
            largest_value = temp1
        if temp2 > largest_value:
            largest_value = temp2

    return largest_value


def select_sections(occupancy_probability):
    """
    Function description: This function returns a list of tuple(n,m), where n represents the row and m represents the column.
                            The list of tuples represents a path through which it can find the smallest summation value from row 0 to row n.
                            Given that only one column can be chosen for each row, the selected column can only go to adjacent or same columns (there are three possible paths)
                            , and the total selected column of each row must give the smallest value among the other possible paths. Thus,this function find a path that give the smallest summation
                            and return the value of the smallest summation.

    Approach description: The algorithm's concept is similar to the maze presented in tutorial week 7. The maze will begin at the exit and will layout all possible paths from the exit to the beginning.
                            After laying all the paths, the user will select the path that will benefit him the most.
                            The algorithm for the select_sections function will be the same as well. First, I'll write a function that creates a maze-like layout of all possible paths.
                            To accomplish this, I devise an algorithm in which the sum of column(m) from row(n) and column(m+1) from row(n+1) that is adjacent or the same as the selected columns is smaller.
                            That is, I will loop through every column in every row and replace any value that is less than the previous sum of column(m) from row(n) and column(m+1) from row(n+1) with the current value summed.
                            After looping through all the columns and rows, the function will return a matrix containing all possible paths. Then, in select_sections, I'll loop through the data from the last row to the first.
                            This produces the desired path, in which it looks for the smallest value adjacent or the same from the selected column from the last row to the first row. As a result, this generates a path as a list of tuples and returns the smallest value.

                            As for the for loop it runs O(nm) time complexity not n^2 because the n  is bigger than m since they are not the same input size they are considered n*m time complexity.

                            It requires a auxiliary space of O(nm) because it needs a temporary space to store the modified matrix as for the original matrix(occupancy_probability) is for reference.

                            The input size is O(nm) because the occupancy_probability is a matrix with n x m.

    Pre-condition: A matrix(n x m) where row(n) must be bigger than the value of column(m) to form a triangle.
    Post-condition: A list of locations (n,m) where the length of the list will be the equivalent to the numbers of row(n)
                    with a unique identity from 0...n-1.

    Input:
        occupancy_probability: A list of lists. Row(n) interior lists with all interior lists are length m(column).
                                occupancy_probability[n][m] represent an integer from 0 to 100 (inclusive).
    Return:
        total: A summation in which only one value/column from each row down to the last row is chosen, and the summation is the smallest among the rest.
        result: A list of tuple (n, m) where the size is the number of rows in a matrix and each tuple
                represents the value chosen with row(n) and column(m) to create a path that returns the sum of minimums from row 0 to row n.

    Time complexity:
        Best: O(nm) , where n is the number of rows and m represent the number column in a nested list.
        Worst: O(nm) , where n is the number of row and m represent the number column in a nested list.
    Space complexity:
        Input: O(nm), where n is the number of row and m represent the number column in a nested list.
        Aux: O(nm), where n is the number of row and m represent the number column in a nested list.

    """
    result = []  # to store a list of tuple, the path
    total = 0  # the total minimum occupancy rate
    min_value = 0  # temporary variable to store min value for each loop/row
    min_index = 0  # temporary variable to store index value for each loop/row
    modified_matrix = modified_sum_matrix(
        occupancy_probability)  # modified the matrix where it sum all the possible column to form a path.

    for i in range(len(modified_matrix) - 1, -1, -1):
        if i == len(modified_matrix) - 1:  # first time run find the min value for the last row.
            min_value = find_minimum(modified_matrix[i])
            min_index = modified_matrix[i].index(min_value)
            result.insert(0, (i, min_index))
            total = min_value
        else:
            temp2 = modified_matrix[i][min_index]
            if min_index == 0:  # if the column is located at the leftmost of a list.
                temp3 = modified_matrix[i][min_index + 1]
                min_value = find_minimum([temp2, temp3])
            elif min_index != len(
                    modified_matrix[0]) - 1:  # if the column is located at between start and end of the list.
                temp1 = modified_matrix[i][min_index - 1]
                temp3 = modified_matrix[i][min_index + 1]
                min_value = find_minimum([temp1, temp2, temp3])
            elif min_index == len(modified_matrix[0]) - 1:  # if the column is located at the end of the list.
                temp1 = modified_matrix[i][min_index - 1]
                min_value = find_minimum([temp1, temp2])

            min_index = find_index(modified_matrix[i], min_value)
            result.insert(0, (i, min_index))

    return [total, result]


def find_minimum(input_list):
    """
    Function description: This function return the smallest value of a list.

    Pre-condition: A list of numbers.
    Post-condition: An integer that is the smallest value in the list.

    Input:
        input_list: A list of numbers.

    Return:
        result: the smallest value in the list.

    Time complexity:
        Best: O(n) , n a list of numbers.
        Worst: O(n) , a list of numbers. The loop had to start from 0 ... n no matter what.
    Space complexity:
        Input: O(n) , n a list of numbers.
        Aux: O(1) , no extra additional space needed. (no extra list)
    """

    result = input_list[0]
    for i in range(1, len(input_list)):
        if input_list[i] < result:
            result = input_list[i]

    return result


def find_index(input_list, min_value):
    """
    Function description: This function find the index of the smallest value in a list.

    Pre-condition: A list and the smallest value in the list.
    Post-condition: An index of the smallest value in a list.

    Input:
        input_list: A list of numbers.
        min_value: The smallest number in a list.
    Return:
        result: An integer that represent the smallest value in a list.

    Time complexity:
        Best: O(1) , the first loop where the smallest value located at index 0.
        Worst: O(n) , the smallest value at the end of the list, at index n.
    Space complexity:
        Input: O(n) , n a list of numbers.
        Aux: O(1) , no extra additional space needed. (no extra list)
    """
    result = 0
    for i in range(len(input_list)):
        if input_list[i] == min_value:
            result = i

    return result


def modified_sum_matrix(occupancy_probability):
    """
    Function description: A function that loop through every row and column. In the nested loop a sum of column(m) from row(n) and [column(m+1) or column(m) or column(m-1)] from row(n+1) that is adjacent or the same as the selected columns that is smaller.
                        The smaller value will then store in a temporary list of lists or matrix. After finish looping from first to last row a modified matrix with all possible summation that gives the smallest value will be shown in the last row.

    Precondition: A matrix(n x m) where row(n) must be bigger than the value of column(m) to form a triangle.
    Postcondition: A matrix that is modified where all selected column/value from first row to last row is summed.

    Input:
        occupancy_probability: A list of lists. Row(n) interior lists with all interior lists are length m(column).
                                occupancy_probability[n][m] represent an integer from 0 to 100 (inclusive).
    Return:
        modified_matrix: A matrix or list of lists that sums all possible values/columns from first row to the last row.

    Time complexity:
        Best: O(nm) , where n is the number of rows and m represent the number column in a nested list.
        Worst: O(nm) , where n is the number of rows and m represent the number column in a nested list.
    Space complexity:
        Input: O(nm) , where n is the number of rows and m represent the number column in a nested list.
        Aux: O(nm) , where n is the number of rows and m represent the number column in a nested list.

    """
    num_row = len(occupancy_probability)
    num_col = len(occupancy_probability[0])
    modified_matrix = initialize_matrix(num_row, num_col)
    modified_matrix[0] = occupancy_probability[0]

    for row in range(len(occupancy_probability) - 1):  # 0
        for column in range(len(occupancy_probability[0])):
            if column == 0:
                modified_matrix[row + 1][column] = occupancy_probability[row + 1][column] + modified_matrix[row][column]
                modified_matrix[row + 1][column + 1] = occupancy_probability[row + 1][column + 1] + \
                                                       modified_matrix[row][column]

            elif 0 < column < len(occupancy_probability[0]):  # between 1-3
                # check and compare the value of the left adjacent value whether it is smaller or not
                temp1 = occupancy_probability[row + 1][column - 1] + modified_matrix[row][column]
                if temp1 < modified_matrix[row + 1][column - 1]:
                    modified_matrix[row + 1][column - 1] = temp1

                # check and compare the value of the same adjacent value whether it is smaller or not
                temp2 = occupancy_probability[row + 1][column] + modified_matrix[row][column]
                if temp2 < modified_matrix[row + 1][column]:
                    modified_matrix[row + 1][column] = temp2

                # store the value of the right adjacent value
                if column != len(occupancy_probability[0]) - 1:
                    modified_matrix[row + 1][column + 1] = occupancy_probability[row + 1][column + 1] + \
                                                           modified_matrix[row][column]

    return modified_matrix


def initialize_matrix(N, M):
    """
     Function description: Generate an empty matrix or list of lists (nxm) where n is row and m is column.

     Precondition: n, number of row and  m , number of column.
     Postcondition: a list os lists (matrix).

     Input:
        N: number of rows.
        M: number of columns.
    Return:
        matrix: A matrix or list of lists (N x M) with 0 value.

    Time complexity:
        Best: O(nm) , where n is the number of rows and m represent the number column.
        Worst: O(nm) , where n is the number of rows and m represent the number column.
    Space complexity:
        Input: O(nm) , where n is the number of rows and m represent the number column.
        Aux: O(nm) , where n is the number of rows and m represent the number column.

    """
    matrix = [[0 for i in range(M)] for j in range(N)]

    return matrix


if __name__ == "__main__":
    # Example
    start = 0
    end = 4
    # The locations where there are potential passengers
    passengers = [2, 1]
    # The roads represented as a list of tuple
    roads = [(0, 3, 5, 3), (3, 4, 35, 15), (3, 2, 2, 2), (4, 0, 15, 10),
             (2, 4, 30, 25), (2, 0, 2, 2), (0, 1, 10, 10), (1, 4, 30, 20)]

    # Your function should return the optimal route (which takes 27 minutes).
    print(optimalRoute(start, end, passengers, roads))
    # graph = Graph()
    # graph.add_vertex(0)
    # graph.add_vertex(1)
    # graph.add_vertex(2)
    # graph.add_vertex(3)
    #
    # graph.add_edge(1,2,3)
    # graph.add_edge(2,3,4)
    #
    #
    #
    # roads = [(0, 3, 5), (3, 4, 35), (3, 2, 2), (4, 0, 15),
    #          (2, 4, 30), (2, 0, 2), (0, 1, 10), (1, 4, 30)]
    # MinH = min_heap()
    # first_vertex = graph.vertices[0]
    # first_vertex.distance = 10
    # MinH.insert((first_vertex, first_vertex.distance))
    # sec_vertex = graph.vertices[1]
    # sec_vertex.distance = 4
    # MinH.insert((sec_vertex, sec_vertex.distance))
    #
    #
    # for i in range(MinH.length):
    #     value = MinH.serve()
    #     print(value)
    #     print(value[0])
    #     print(value[1])
    #     print(value[0].distance)

    # Example
    occupancy_probability = [
        [31, 54, 94, 34, 12],
        [26, 25, 24, 16, 87],
        [39, 74, 50, 13, 82],
        [42, 20, 81, 21, 52],
        [30, 43, 19, 5, 47],
        [37, 59, 70, 28, 15],
        [2, 16, 14, 57, 49],
        [22, 38, 9, 19, 99]]

    # occupancy_probability = [[32, 86, 95, 15, 68, 90],
    #                          [91, 88, 96, 51, 64, 66],
    #                          [17, 70, 13, 9, 90, 17],
    #                          [17, 15, 38, 12, 53, 17],
    #                          [29, 6, 18, 27, 66, 48],
    #                          [74, 43, 76, 44, 3, 1],
    #                          [89, 1, 8, 24, 45, 62],
    #                          [3, 98, 99, 89, 6, 66]]
    print(select_sections(occupancy_probability))
