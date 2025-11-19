# ProgrammingChallenge

Outline:

Programming challenge Software Architecture
For the first step I will split the program into three classes: one corresponding to an agent, one corresponding to the grid, and one corresponding to the object itself. The addition of the object class allows movement logic, object state, and positional information to be separated from the environment.
The grid class will have N and M passed into it to create the size of the array, it will also have coordinates of the goal object, and E which will stand for the grid’s border coordinates and identify which cells will penalise the player for having their object over them. The grid will no longer contain movement logic, but will instead store environmental constraints and provide checks such as border, bounds, and penalty zones.
The object class will contain the n and m parameters which will identify the length and width of the object, it will also have the object’s current position on the coordinate plane, its current state and next state which may be a linked structure if we want to store state transitions. This class will handle applying actions and determining how the object moves inside the environment by consulting the grid.
The object class will have the following methods: identify_initial_position to determine the starting placement of the object on the grid, apply_action which will move the object in the direction produced by agents after checking the grid for validity, footprint_cells to identify which cells of the grid the object occupies at any moment.
The agent class will have the weight ranging from 0 to 5, the action {left, right, up, down, still} identified also as an ENUM or an int 0–4, and the position which will identify its position on the coordinate plane.
The agent class will have the following methods: generate_action will randomly assign one of the 5 movement directions and assign it to an agent; determine_weight will identify the weight of an agent by its relation to the object.
Program Flow:
The program will start by accepting the user input for the desired lengths of objects as well as the grid.
Next we will start the loop of random generation of each of the agents values by determining its relative position to object cell expansion.
Next we will check for the edge placements whether or not an object is hovering over them.
After that's done we will push agent data into the object class which will allow us to determine the movement direction through weight determination method.
After that's done we will pass an argument to the grid class and reflect the object's transition on the corresponding grid cells.
After everything is done we will receive new information about object placement and will start the process all over again.

Brainstorming with GenAI Tools
a) In Part II, we understood software architecture as a roadmap, a high-level plan
that classifies system components, interactions, and responsibilities without
using full implementation details. We used a summarized prompt of the
assignment and put that intro ChatGPT and Claude AI. The prompt we used is as
follows:
“I am working on a broken down version of the Discrete Smart Surface benchmark. The
system we are using involves an N×M grid of agents. Each agent decides a random
action {up, down, left, right, still}. The environment will collect all actions, apply weighted
voting based on which agents are under, by, or away from a 2D object placed on the
grid, and move the object as requested. Penalty zones should exist on the left and right
borders, and the goal is to move the object from an initial state to its terminal goal state.
Could you help me brainstorm possible software architectures (as high-level roadmaps)
for implementing this simulation? What classes, interactions, or design patterns might
be useful? Do not give me a final answer, rather brainstorm for me.”

b) Tool 1: ChatGPT (Model: GPT-4o / GPT-5)
 ChatGPT emphasized:
- Separation of concerns between Environment, Agent, and Object.
- Suggested using the Strategy pattern for agent behaviors, allowing future
extensibility.
- Proposed a MovementResolver class to encapsulate weighted voting, which
influenced my revised architecture.
- Highlighted benefits of immutability in State transitions.
 This made us realize our Part I environment class was too overloaded, so we
decided to separate the action-aggregation logic into a new helper method.
Tool 2: Claude 3.5 / Gemini Advanced
Claude/Gemini suggested that:
- Modeling the grid as a “world model object” containing spatial relationships.
- Encouraged using a Message-Passing design (each agent “submits” actions),
aligning with multi-agent simulation patterns.
- Suggested defining interfaces like IEnvironmentObserver to allow for better
visualization modules in future.
- Proposed storing agent roles (under, by, other) as their own class hierarchy.
This helped us think differently about extensibility and possible future ways of
integrating.

c) I personally liked GPT suggestions more on this stage of the project as they are more relevant considering current structure, meanwhile Claude and Gemini are more suggestive of larger scale improvements which may not be as easily implemented or understood and often do refer to more specific objectives. However some of the stuff like EnvironmentObserver is useful to implement in the later stages of the program for its performance to be improved.

d) After considering the suggestions the revisited plan will look like this:
Programming challenge Software Architecture
 For the first step I will split the program into three classes: one corresponding to an agent, one corresponding to the grid, and one corresponding to the object itself. The addition of the object class allows movement logic, object state, and positional information to be separated from the environment. Following ChatGPT’s suggestion, I will also introduce a separate MovementResolver class that will handle weighted voting and produce the final movement direction, reducing the workload of the environment.
The grid class will have N and M passed into it to create the size of the array, it will also have coordinates of the goal object, and E which will stand for the grid’s border coordinates and identify which cells will penalise the player for having their object over them. The grid will no longer contain movement logic, but will instead store environmental constraints and provide checks such as border, bounds, and penalty zones. This follows ChatGPT’s emphasis on separating concerns so that the grid only enforces rules and does not interpret agent behavior.
The object class will contain the n and m parameters which will identify the length and width of the object, it will also have the object’s current position on the coordinate plane, its current state and next state which will be generated immutably as suggested by ChatGPT to avoid unintended side-effects. This class will handle applying actions and determining how the object moves inside the environment by consulting the grid. The use of immutable next states allows easier debugging and clearer transitions between steps.
The object class will have the following methods: identify_initial_position to determine the starting placement of the object on the grid, apply_action which will move the object in the direction produced by agents after checking the grid for validity, and footprint_cells to identify which cells of the grid the object occupies at any moment. The actual resolution of actions will not happen here anymore, since that is delegated to the new MovementResolver class.
The agent class will have the weight ranging from 0 to 5, the action {left, right, up, down, still} identified also as an ENUM or an int 0–4, and the position which will identify its position on the coordinate plane. Based on ChatGPT’s suggestion, the generation of actions will use a Strategy pattern, allowing different behaviors to be swapped in later without changing the agent class itself.
The agent class will have the following methods: generate_action will randomly assign one of the 5 movement directions and assign it to an agent through a behavior strategy; determine_weight will identify the weight of an agent by its relation to the object. This weight will be used by the MovementResolver to determine the final direction.
Program Flow:
 The program will start by accepting the user input for the desired lengths of objects as well as the grid.
 Next we will start the loop of random generation of each of the agents values by determining its relative position to object cell expansion.
 Next we will check for the edge placements whether or not an object is hovering over them.
 After that’s done we will push agent data into the MovementResolver class which will collect all actions, apply weighted voting, and determine the movement direction.
 After that’s done we will pass this resulting direction to the object class which will apply the movement (after consulting the grid for validity) and generate a new immutable object state.
 After everything is done we will reflect the object's transition on the corresponding grid cells.
 After everything updates we will receive new information about object placement and will start the process all over again.

