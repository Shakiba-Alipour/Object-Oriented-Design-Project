package org.ood;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class DAG {
    Graph<Object, DefaultEdge> graph;

    public DAG() {
        this.graph = new SimpleGraph<>(DefaultEdge.class);
    }

    public void add_nodes(String vertex) {
        this.graph.addVertex(vertex);
    }

    public void add_edges(String vertex1, String vertex2, int relationship) {
        DefaultEdge edge = this.graph.addEdge(vertex1, vertex2);
        this.graph.setEdgeWeight(edge, relationship);
    }

    public void save() throws IOException {
        JGraphXAdapter<Object, DefaultEdge> graphAdapter = new JGraphXAdapter<>(this.graph);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2,
                new Color(0f, 0f, 0f, .5f), true, null);
        File png_image = new File("src/test/java/graph.png");
        ImageIO.write(image, "PNG", png_image);
        File jpg_image = new File("src/test/java/graph.jpg");
        ImageIO.write(image, "JPG", jpg_image);
    }

}