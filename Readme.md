### Rommel D. Bagasina
Note: In every project there is a demo video or demo images included.

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
# Projects

🚚 **Spotter Labs — Freight Rate Prediction**

**Problem:** Freight rates vary based on factors such as pickup and delivery locations, distance, equipment type, shipment weight, market conditions, and quote activity. The challenge was to build a regression model capable of predicting freight rates from historical load data and then generate predictions for unseen loads, including a fixed-lane December forecast.

**Solution:** I built a machine learning pipeline combining:

* Data cleaning and preprocessing for numerical, categorical, and date-based features
* Chronological train/validation splitting to better reflect real-world forecasting
* Feature engineering from shipment dates
* CatBoost regression for handling nonlinear relationships and categorical variables
* Model evaluation using RMSE against a mean-prediction baseline
* Hyperparameter experimentation with different tree depths and early stopping
* Batch prediction for unseen validation loads and a fixed December forecasting dataset
* Automated scoring using the provided assessment scorer

The model uses shipment characteristics including pickup and delivery locations, geographic coordinates, distance, equipment, weight, market index, quote signal, and date-derived information to estimate the posted freight rate.

**Engineering problems explored:**

* How do you validate a model when the data has a temporal ordering?
* How do you prevent future information from influencing model validation?
* How do you handle both categorical and numerical features in the same model?
* How do you determine whether a model is actually learning useful patterns rather than simply predicting the average?
* How do you select model complexity without overfitting?
* How do you handle missing and inconsistent data before it reaches the model?
* How do you turn a trained model into a reproducible prediction pipeline for unseen data?
* How do you evaluate a forecasting model when the eventual prediction period differs from the training data?

**Results:** The CatBoost model substantially improved over the mean-prediction baseline, reducing validation RMSE from approximately **1525** to approximately **637**.

**Technologies:** Python, Pandas, NumPy, CatBoost, Scikit-learn, Matplotlib

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
