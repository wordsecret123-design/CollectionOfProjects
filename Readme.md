### Rommel D. Bagasina

### Software Engineer & Low-Level Builder

📍 Remote / Philippines (Willing to overlap with international time zones) 

Welcome to my portfolio! This repository serves as a centralized hub for engineering projects developed during my academic career. These applications represent rapid prototyping under strict, short-term architectural and timeline constraints. 

My technical interests bridge the gap between low-level hardware design and modern, scalable networking software. 

### Featured Projects

A collection of systems I built to understand and solve engineering problems across software, embedded systems, networking, computer vision, digital logic, and low-level programming.

I tend to approach projects from the engineering problem first:

«What needs to happen, what constraints make it difficult, and what system can I build to make it work?»

The projects below are experiments in solving those problems from the underlying mechanics rather than relying entirely on high-level abstractions.

---

Projects

🥚 QuackSort — Automated Egg Sorting Machine

Problem:
Manually inspecting and sorting eggs is repetitive and difficult to perform consistently. The challenge was to build a system capable of analyzing physical objects and then translating that analysis into reliable mechanical actions.

Solution:
I built a distributed hardware/software system combining:

- Computer vision for analyzing eggs
- Python for image processing and system logic
- Raspberry Pi for hardware-side control
- ESP32 for motor control
- WebSockets for communication between machines
- Stepper motors and conveyors for physical sorting

The system separates perception from physical control: a computer performs the computationally expensive analysis, while the embedded hardware handles the physical machinery.

Engineering problems explored:

- How do you communicate reliably between heterogeneous devices?
- How do you translate a software classification into a physical action?
- How do you coordinate computation happening on different machines?
- How do you deal with timing and synchronization between software and hardware?
- How do you design a system where individual components can be replaced without redesigning everything?

Technologies: Python, OpenCV, Raspberry Pi, ESP32, WebSockets, stepper motors

---

💬 Multi-Client Chatroom

Problem:
A chat application appears simple from the user's perspective, but a server must simultaneously maintain multiple network connections, receive messages, distribute them to other clients, and handle clients connecting and disconnecting at arbitrary times.

Solution:
I implemented a multi-client chat system in C, working directly with networking and concurrency rather than using a high-level networking framework.

The project explores the underlying mechanics of real-time communication:

- TCP connections
- Multiple simultaneous clients
- Concurrent communication
- Message handling
- Client connection/disconnection
- Server-side coordination

Engineering problems explored:

- How can one server communicate with many clients at the same time?
- How should concurrent connections be managed?
- What happens when a client unexpectedly disconnects?
- How does information move from one process to another across a network?

Technologies: C, TCP/IP, sockets, concurrency

---

🔢 Gray-Code Simulator

Problem:
Digital systems often need to represent state transitions in ways that minimize ambiguity when multiple bits change simultaneously. Gray code addresses this by ensuring adjacent values differ by only one bit.

The challenge was to model and verify that behavior programmatically.

Solution:
I implemented a simulator for generating and analyzing Gray-code sequences, allowing the transition behavior to be examined rather than treated as an abstract mathematical property.

Engineering problems explored:

- How can a mathematical representation be translated into digital logic?
- How can state transitions be verified programmatically?
- How can simulations be used to validate digital-system behavior before hardware implementation?

Technologies: Verilog, digital logic, simulation

---

🖥️ x86 Assembly POS System

Problem:
High-level programming languages hide many details of how software interacts with a processor. I wanted to explore what happens when those abstractions are removed.

Solution:
I implemented a point-of-sale system using x86 assembly, working directly with processor-level operations and memory.

This project focuses on the mechanics underneath ordinary software:

- Registers
- Memory
- Arithmetic operations
- Control flow
- Function calls
- Data representation

Engineering problems explored:

- How do high-level program structures map onto processor instructions?
- How is state represented in memory?
- How does a processor actually execute the logic represented by a program?

Technologies: x86 Assembly

---
⚙️ CPU Scheduling Calculator

Problem:
Operating systems must decide how CPU time is distributed among multiple processes, balancing factors such as arrival time, execution time, and scheduling priority. Different scheduling algorithms can produce significantly different execution orders and performance characteristics.

The challenge was to model these scheduling decisions programmatically and calculate the resulting execution behavior.

Solution:
I implemented a CPU scheduling calculator that simulates process execution under different scheduling algorithms, producing the resulting schedules and performance metrics.

Engineering problems explored:

* How can operating-system scheduling policies be translated into executable algorithms?
* How can process arrival and execution times be modeled accurately?
* How can scheduling algorithms be simulated to compare their behavior and performance?
* How can execution schedules and timing metrics be calculated from a sequence of scheduling decisions?

Technologies: C++, operating systems, CPU scheduling, algorithmic simulation

---

Engineering Focus

Although the projects use different technologies, they share a common theme:

Understanding the system underneath the abstraction.

Some examples:

Computer vision → physical machine

How can information extracted from an image ultimately control a motor?

Networking → distributed system

How can independent machines exchange information and coordinate their actions?

C → operating-system concepts

What actually happens when software manages multiple concurrent connections?

Assembly → processor

What does a program become when high-level abstractions are removed?

Verilog → digital hardware

How can computational logic be represented as hardware behavior?

---

What I'm Currently Exploring

I'm currently working toward implementing a decoder-only Transformer from scratch using NumPy, without relying on a deep-learning framework.

The goal is not simply to use a Transformer, but to understand the mechanisms that make one work:

- Embeddings
- Positional representations
- Query, Key, and Value transformations
- Scaled dot-product attention
- Multi-head attention
- Residual connections
- Layer normalization
- Feed-forward networks
- Backpropagation
- Adam optimization

This continues the same approach as the projects above:

«Understand the abstraction by rebuilding the mechanism underneath it.»

---

Technologies

Languages

- C
- C++
- Python
- x86 Assembly
- Verilog

Systems & Hardware

- Raspberry Pi
- ESP32
- TCP/IP
- WebSockets
- Stepper motors
- Digital logic

Software

- OpenCV
- NumPy
- Git

---

Why These Projects?

I am interested in engineering problems where the solution requires understanding how multiple layers of a system interact.

That includes systems programming, networking, embedded systems, robotics, computer vision, and machine learning.

Rather than specializing exclusively in one abstraction layer, I like being able to move between them:

hardware → low-level software → systems → algorithms → machine learning

This repository documents that process.
