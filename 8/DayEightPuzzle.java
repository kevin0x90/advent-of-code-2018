import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

class Header {
    private int numberOfChildNodes;
    private int numberOfMetadataEntries;

    public Header(final int numberOfChildNodes, final int numberOfMetadataEntries) {
        this.numberOfChildNodes = numberOfChildNodes;
        this.numberOfMetadataEntries = numberOfMetadataEntries;
    }

    public int getNumberOfChildNodes() {
        return numberOfChildNodes;
    }

    public int getNumberOfMetadataEntries() {
        return numberOfMetadataEntries;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Header)) {
            return false;
        }

        final Header otherHeader = (Header) other;

        return numberOfMetadataEntries == otherHeader.numberOfMetadataEntries &&
                numberOfChildNodes == otherHeader.numberOfChildNodes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfChildNodes, numberOfMetadataEntries);
    }

    @Override
    public String toString() {
        return "[ChildNodes: " + numberOfChildNodes + " MetadataEntries: " + numberOfMetadataEntries + "]";
    }
}

class Node implements Comparable<Node> {
    private final int id;
    private final Header header;
    private final Map<Integer, Node> childNodes;
    private final List<Integer> metadata;

    public Node(final Header header, final int id) {
        this.id = id;
        this.header = header;
        childNodes = new TreeMap<>();
        metadata = new ArrayList<>();
    }

    public void fill(final Scanner scanner) {
        for (int i = 0; i < header.getNumberOfChildNodes(); ++i) {
            final Header childHeader = new Header(scanner.nextInt(), scanner.nextInt());
            final Node childNode = new Node(childHeader, i + 1);
            childNode.fill(scanner);
            childNodes.put(childNode.id, childNode);
        }

        for (int i = 0; i < header.getNumberOfMetadataEntries(); ++i) {
            metadata.add(scanner.nextInt());
        }
    }

    public int getSumOfMetadata() {
        int sum = metadata.stream().reduce(0, Integer::sum);

        for (final Node childNode : childNodes.values()) {
            sum += childNode.getSumOfMetadata();
        }

        return sum;
    }

    public int getNodeValue() {
        int sum = 0;

        if (header.getNumberOfChildNodes() == 0) {
            return metadata.stream().reduce(0, Integer::sum);
        }

        for (final int data : metadata) {
            final Node childNode = childNodes.get(data);
            if (childNode != null) {
                sum += childNode.getNodeValue();
            }
        }

        return sum;
    }

    public int compareTo(final Node otherNode) {
        return Integer.compare(id, otherNode.id);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof Node)) {
            return false;
        }

        final Node otherNode = (Node) other;

        return header.equals(otherNode.header) && childNodes.equals(otherNode.childNodes) &&
                metadata.equals(otherNode.metadata) && id == otherNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, childNodes, metadata, id);
    }

    @Override
    public String toString() {
        return "[Id: " + id + " Header: " + header + " ChildNodes: " + childNodes + " Metadata: " + metadata + "]";
    }
}

class Solver {
    public static void main(final String[] args) throws FileNotFoundException {
        final File input = new File("C:\\Users\\k3v1n\\Documents\\adventOfCode\\8\\DayEightPuzzle.txt");

        try (final Scanner scanner = new Scanner(input)) {
            while (scanner.hasNext()) {
                final Header rootHeader = new Header(scanner.nextInt(), scanner.nextInt());
                final Node rootNode = new Node(rootHeader, 1);
                rootNode.fill(scanner);

                System.out.println(rootNode.getSumOfMetadata());
                System.out.println(rootNode.getNodeValue());
            }
        }
    }
}