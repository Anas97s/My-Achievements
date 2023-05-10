/**
 * @file graph.cpp
 */
#include "graph.h"
#include <algorithm>

/**A vertex's ID is its index in Graph::vertices*/
int verIndexID = 0;

/**An Edge's ID is its index in Graph::edges*/
int edgeIndexID = 0;

// Complete the following constructor:
Vertex::Vertex(std::string name, size_t vertexId){
	this->name = name;
	this->id = vertexId;
}   

void Vertex::addInEdgeId(size_t edgeId){
 
	this->inEdgeIds.emplace_back(edgeId);
} 

void Vertex::addOutEdgeId(size_t edgeId)
{
   
	this->outEdgeIds.emplace_back(edgeId);
}

// Complete the following constructor:
Edge::Edge(size_t edgeId, size_t inVertexId, size_t outVertexId){
	this->id = edgeId;
	this->inVertex = inVertexId;
	this->outVertex = outVertexId;
}

/**
 * Factory function to make a new Vertex
 * @returns Id of the new Vertex
 */
size_t Graph::makeVertex(const std::string& name)
{
  
	Vertex ver(name, verIndexID);
	verIndexID++;
	this->vertices.emplace_back(ver);
	
    return ver.getId(); 
}

/**
 * Factory function to make a new Edge
 * @returns Id of the new edge
 */
size_t Graph::makeEdge(const size_t inVertexId, const size_t outVertexId)
{
  
	
	Edge edg(edgeIndexID, inVertexId, outVertexId);
	
	this->edges.emplace_back(edg);
	this->vertices[inVertexId].addOutEdgeId(edgeIndexID);
	this->vertices[outVertexId].addInEdgeId(edgeIndexID);
	edgeIndexID++;
	
    return edg.getId(); 
}

Vertex& Graph::getVertex(size_t id) { return this->vertices[id]; }
Edge& Graph::getEdge(size_t id) { return this->edges[id]; }

const Vertex& Graph::getVertex(size_t id) const { return this->vertices[id]; }
const Edge& Graph::getEdge(size_t id) const { return this->edges[id]; }

void printGraph(std::ostream& os, const Graph& graph)
{
    os << "-------------------------------------------" << std::endl;
    for(const Vertex& vertex: graph.getVertices())
    {
        os << "Vertex Name: " << vertex.getName() << std::endl;
        os << "Input Edges: " << std::endl;
        if(vertex.getInEdgeIds().empty())
        {
            os << " " << std::endl;
        }
        else
        {
            for(const size_t& inEdgeId: vertex.getInEdgeIds()) {
                os << "Edge ID: " << inEdgeId << std::endl;
            }
        }
        os << "Output Edges: " << std::endl;
        if(vertex.getOutEdgeIds().empty())
        {
            os << " " << std::endl;
        }
        else
        {
            for(const size_t& outEdgeId: vertex.getOutEdgeIds()) {
                os << "Edge ID: " << outEdgeId << std::endl;
            }
        }
        os<<"\n";
    }
    os << "-------------------------------------------" << std::endl;
}


