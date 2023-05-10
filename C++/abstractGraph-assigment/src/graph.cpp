#include <cmath>
#include <tuple>
#include <iostream>
#include "graph.h"

std::ostream& operator<<(std::ostream& os, const Graph& graph)
{
    os << "Nodes : \n";
    graph.forAllNodes([&](std::size_t nodeId)
        {
            os<<nodeId<<'\n';
        });
    os << "Edges : \n";
    graph.forAllEdges([&](std::size_t edgeId)
        {
            auto [from, to] = graph.getAdjacentNodeIds(edgeId);
            os<<edgeId<<" : "<<from<<"->( "<<graph.getEdgeWeight(edgeId)<<" )->"<<to<<'\n';
        });
    os.flush();
    return os;
}


AdjacentGraph::AdjacentGraph() : node(), edgeM() {}

std::size_t AdjacentGraph::addEdge(std::size_t nodeId1, std::size_t nodeId2, double weight){
	int newEdge = 0;
	
	if(nodeId1 < this->node.size() && nodeId2 < this->node.size()){
		for(unsigned int i = 0; i < this->node.size(); i++){
			for(unsigned int j = 0; j < this->node.size(); j++){
				if(i == nodeId1 && j == nodeId2){
					this->edgeM[i][j] = weight;
				}
				newEdge++;
			}
		}
	}
	
	return newEdge;
}

std::size_t AdjacentGraph::addNode(){
	size_t idOfnewNode = this->node.size();
	this->node.emplace_back(idOfnewNode);
	
	this->edgeM.clear();
	this->edgeM.resize(this->node.size(), std::vector<double>(this->node.size()));
	
	return idOfnewNode;
}

std::tuple<std::size_t, std::size_t> AdjacentGraph::getAdjacentNodeIds(std::size_t edgeId) const{
	size_t index = 0;
	std::tuple<std::size_t, std::size_t> connected(0,0);
	for(unsigned int i = 0; i < this->node.size(); i++){
		for(unsigned int j = 0; j < this->node.size(); j++){
			if(index == edgeId){
				connected = std::tuple<std::size_t, std::size_t> (i,j);
			}
			index++;
		}
	}
	
	return connected;
}


std::vector<std::size_t> AdjacentGraph::getInEdgeIds(std::size_t nodeId) const{
	size_t index = 0;
	std::vector<std::size_t> inEdges;
	for(unsigned int i = 0; i < this->node.size(); i++){
		for(unsigned int j = 0; j < this->node.size(); j++){
			if(j == nodeId){
				inEdges.emplace_back(index);
			}
			index++;
		}
	}
	
	return inEdges;
}


std::vector<std::size_t> AdjacentGraph::getOutEdgeIds(std::size_t nodeId) const{
	size_t index = 0;
	std::vector<std::size_t> outEdges;
	
	for(unsigned int i = 0; i < this->node.size(); i++){
		for(unsigned int j = 0; j < this->node.size(); j++){
			if(j == nodeId){
				outEdges.emplace_back(index);
			}
			index++;
		}
	}
	
	return outEdges;
}


void AdjacentGraph::setEdgeWeight(std::size_t edgeId, double weight){
	size_t index = 0;
    for (unsigned int i = 0; i < this->node.size(); i++){
        for (unsigned int j = 0; j < this->node.size(); j++){
            if (index == edgeId){
                this->edgeM[i][j] = weight;
            }
            index++;
        }
    }
}

double AdjacentGraph::getEdgeWeight(std::size_t edgeId) const{
	size_t index = 0;
    double result = 0;
    for (unsigned int i = 0; i < this->node.size(); i++){
        for (unsigned int j = 0; j < this->node.size(); j++){
            if (index == edgeId){
                result = this->edgeM[i][j];
            }
            index++;
        }
    }
    return result;
}


void AdjacentGraph::forAllEdges(const std::function<void(std::size_t edgeId)>& function) const{
	size_t index = 0;
    for (unsigned int i = 0; i < this->node.size(); i++){
        for (unsigned int j = 0; j < this->node.size(); j++){
            if (this->edgeM[i][j] > 0){
                function(index);
            }
            index++;
        }
    }
}

void AdjacentGraph::forAllNodes(const std::function<void(std::size_t nodeId)>& function) const{
	for (unsigned int i = 0; i < this->node.size(); i++){
        function(this->node.at(i));
    }
}

ClassicGraph::ClassicGraph() {}

std::size_t ClassicGraph::addEdge(std::size_t nodeId1, std::size_t nodeId2, double weight){
	size_t idOfNewEdge = 0;
	ClassicGraph::Edge now;
	
	if(nodeId1 < this->node.size() && nodeId2 < this->node.size()){
		now.setID_1(nodeId1);
		now.setID_2(nodeId2);
		now.setWeight(weight);
		this->edge.emplace_back(now);
		size_t length = this->node.size();
		idOfNewEdge = length - 1;
	}
	
	return idOfNewEdge;
}

std::size_t ClassicGraph::addNode(){
    this->node.resize(this->node.size() + 1);
	
    return this->node.size();
}


std::tuple<std::size_t, std::size_t> ClassicGraph::getAdjacentNodeIds(std::size_t edgeId) const{
    std::tuple<std::size_t, std::size_t> connected(0, 0);
	
    for (unsigned int i = 0; i < this->edge.size(); i++){
        if (i == edgeId){
            connected = std::tuple<std::size_t, std::size_t>(this->edge.at(i).get_nID_1(), this->edge.at(i).get_nID_2());
        }
    }
    return connected;
}

std::vector<std::size_t> ClassicGraph::getInEdgeIds(std::size_t nodeId) const{
    std::vector<std::size_t> inEdges;
    for (unsigned int i = 0; i < this->node.size(); i++)
    {
        if (i == nodeId)
        {
            inEdges.emplace_back(this->node.at(i).getInComing().at(i));
        }
    }
    return inEdges;
}

std::vector<std::size_t> ClassicGraph::getOutEdgeIds(std::size_t nodeId) const{
	std::vector<std::size_t> outEdges;
    for (unsigned int i = 0; i < this->node.size(); i++)
    {
        if (i == nodeId)
        {
            outEdges.emplace_back(this->node.at(i).getOutComing().at(i));
        }
    }
    return  outEdges;
}

void ClassicGraph::setEdgeWeight(std::size_t edgeId, double weight){
    for (unsigned int i = 0; i < this->edge.size(); i++)
    {
        if (i == edgeId)
        {
            this->edge.at(i).setWeight(weight);
        }
    }
}

double ClassicGraph::getEdgeWeight(std::size_t edgeId) const{
    double result = 0;
    for (unsigned int i = 0; i < this->edge.size(); i++)
    {
        if (i == edgeId)
        {
            result = this->edge.at(i).getWeight();
        }
    }
    return result;
}

void ClassicGraph::forAllEdges(const std::function<void(std::size_t edgeId)> &function) const{
    for (unsigned int i = 0; i < this->edge.size(); i++)
    {
        function(i);
    }
}

void ClassicGraph::forAllNodes(const std::function<void(std::size_t nodeId)> &function) const{
    for (unsigned int i = 0; i < this->node.size(); i++){
        function(i);
    }
}