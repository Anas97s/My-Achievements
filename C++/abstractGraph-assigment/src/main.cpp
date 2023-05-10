#include "graph.h"
#include <iostream>

// #define BONUS // Uncomment this for additional bonus solution

int main()
{
    std::vector<std::unique_ptr<Graph>> graphs;

    graphs.push_back(std::make_unique<AdjacentGraph>());
    graphs[0]->addNode();
    graphs[0]->addNode();
    graphs[0]->addNode();
    graphs[0]->addNode();
    graphs[0]->addNode();
    graphs[0]->addEdge(0,2,1.0);
    graphs[0]->addEdge(2,4,1.0);
    graphs[0]->addEdge(4,1,1.0);
    graphs[0]->addEdge(1,3,1.0);
    graphs[0]->addEdge(3,0,1.0);

    graphs.push_back(std::make_unique<ClassicGraph>());
    graphs[1]->addNode();
    graphs[1]->addNode();
    graphs[1]->addNode();
    graphs[1]->addNode();
    graphs[1]->addNode();
    graphs[1]->addEdge(0,1,1.0);
    graphs[1]->addEdge(1,2,1.0);
    graphs[1]->addEdge(2,3,1.0);
    graphs[1]->addEdge(3,4,1.0);
    graphs[1]->addEdge(4,0,1.0);

    for(const auto& g: graphs)
    {
        std::cout << *g << std::endl;
    }

#ifdef BONUS
    std::vector<std::unique_ptr<GraphT<double>>> graphTs;

    graphTs.push_back(std::make_unique<AdjacentGraphT<double>>());
    graphTs[0]->addNode();
    graphTs[0]->addNode();
    graphTs[0]->addNode();
    graphTs[0]->addNode();
    graphTs[0]->addNode();
    graphTs[0]->addEdge(0,2,1.0);
    graphTs[0]->addEdge(2,4,1.0);
    graphTs[0]->addEdge(4,1,1.0);
    graphTs[0]->addEdge(1,3,1.0);
    graphTs[0]->addEdge(3,0,1.0);

    graphTs.push_back(std::make_unique<ClassicGraphT<double>>());
    graphTs[1]->addNode();
    graphTs[1]->addNode();
    graphTs[1]->addNode();
    graphTs[1]->addNode();
    graphTs[1]->addNode();
    graphTs[1]->addEdge(0,1,1.0);
    graphTs[1]->addEdge(1,2,1.0);
    graphTs[1]->addEdge(2,3,1.0);
    graphTs[1]->addEdge(3,4,1.0);
    graphTs[1]->addEdge(4,0,1.0);

    for(const auto& g: graphTs)
    {
        std::cout << *g << std::endl;
    }
#endif // BONUS

    return 0;
}

