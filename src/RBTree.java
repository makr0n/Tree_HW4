
    public class RBTree {
        private Node root;
        private int size;

        public int getSize() {
            return size;
        }

        /**
         * Добавляет значение в дерево
         * @param value Добавляемое значение
         * @return Результат операции (успешная или нет)
         */
        public boolean add(int value) {
            if (root == null) {
                root = new Node(value);
                root.setColor(Color.BLACK);
                size++;
                return true;
            }
            boolean result = addTo(root, value);
            root = rebalance(root);
            root.setColor(Color.BLACK);
            return result;
        }

        /**
         * Возвращает черную высоту дерева (количество черных вершин от корня дерева до листьев)
         * @return Черная высота
         */
        public int getBlackHeight() {
            if (root == null) return 0;

            return getBlackHeight(root);
        }

        /**
         * Возвращает высоту дерева (количество ребер в макимальном пути)
         * @return Высота дерева
         */
        public int getTotalHeight() {
            if (root == null) return 0;

            return getTotalHeight(root) - 1;
        }

        /**
         * Возвращает высоту дерева (количество ребер в макимальном пути) начиная с узла node
         * @param node Узел, скотоорго начинается поиск
         * @return Высота дерева
         */
        private int getTotalHeight(Node node) {
            if (node == null) {
                return 0;
            }
            int leftHeight = getTotalHeight(node.getLeftChild()) + 1;
            int rightHeight = getTotalHeight(node.getRightChild()) + 1;

            return Math.max(leftHeight, rightHeight);
        }

        /**
         * Возвращает черную высоту дерева (количество черных вершин от вершины node дерева до листьев)
         * @param node Вершина поддерева
         * @return Черная высота
         */
        private int getBlackHeight(Node node) {
            if (node == null) return 1;
            int leftBlack = getBlackHeight(node.getLeftChild());
            if (!node.isRed()) {
                leftBlack++;
            }
            return leftBlack;
        }

        /**
         * Добавляет значение value после узла node
         * @param node
         * @param value Значение
         * @return Результат добавлениея (успешное или нет)
         */
        private boolean addTo(Node node, int value) {
            if (node.getValue() == value) {
                return false;
            }

            if (node.getValue() > value) {
                if (node.getLeftChild() != null) {
                    boolean result = addTo(node.getLeftChild(), value);
                    Node leftChild = rebalance(node.getLeftChild());
                    node.setLeftChild(leftChild);
                    return result;
                }
                Node newNode = new Node(value);
                newNode.setColor(Color.RED);
                node.setLeftChild(newNode);
                size++;
                return true;
            }

            if (node.getRightChild() != null) {
                boolean result = addTo(node.getRightChild(), value);
                Node rightChild = rebalance(node.getRightChild());
                node.setRightChild(rightChild);
                return result;
            }
            Node newNode = new Node(value);
            newNode.setColor(Color.RED);
            node.setRightChild(newNode);
            size++;
            return true;
        }

        /**
         * Ребалансировка КЧ дерева после добавдения нового узла
         * @param node Узел, к которому применяется ребалансировка
         * @return Исправленный узел
         */
        private Node rebalance(Node node) {
            Node curNode = node;
            boolean needBalance;

            do {
                needBalance = false;
                if (curNode.getRightChild() != null && curNode.getRightChild().isRed()
                        && (curNode.getLeftChild() == null || !curNode.getLeftChild().isRed())) {
                    needBalance = true;
                    curNode = leftTurn(curNode);
                }

                if (curNode.getLeftChild() != null && curNode.getLeftChild().isRed()
                        && curNode.getLeftChild().getLeftChild() != null && curNode.getLeftChild().getLeftChild().isRed()) {
                    needBalance = true;
                    curNode = rightTurn(curNode);
                }

                if (curNode.getRightChild() != null && curNode.getRightChild().isRed()
                        && curNode.getLeftChild() != null && curNode.getLeftChild().isRed()) {
                    needBalance = true;
                    flipColors(curNode);
                }

            } while (needBalance);

            return curNode;
        }

        /**
         * Операция левого поворота при балансировке
         * @param node
         * @return
         */
        private Node leftTurn(Node node) {
            Node right = node.getRightChild();
            node.setRightChild(right.getLeftChild());
            right.setLeftChild(node);
            right.setColor(node.getColor());
            node.setColor(Color.RED);
            return right;
        }

        /**
         * Операция правого поворота при балансировке
         * @param node
         * @return
         */
        private Node rightTurn(Node node) {
            Node left = node.getLeftChild();
            Node tmp = left.getRightChild();
            left.setRightChild(node);
            node.setLeftChild(tmp);
            left.setColor(node.getColor());
            node.setColor(Color.RED);
            return left;
        }

        /**
         * Опреация переворота цвета при балансировке
         * @param node
         */
        private void flipColors(Node node) {
            node.setColor(Color.RED);
            node.getLeftChild().setColor(Color.BLACK);
            node.getRightChild().setColor(Color.BLACK);
        }

        private enum Color {
            RED, BLACK
        }

        /**
         * Узел КЧ дерева
         */
        private class Node {
            private int value;
            private Color color;

            private Node leftChild;
            private Node rightChild;

            public Node(int value) {
                this.value = value;
            }

            public boolean isRed() {
                return color == Color.RED;
            }

            public int getValue() {
                return value;
            }

            public Color getColor() {
                return color;
            }

            public void setColor(Color color) {
                this.color = color;
            }

            public Node getLeftChild() {
                return leftChild;
            }

            public void setLeftChild(Node leftChild) {
                this.leftChild = leftChild;
            }

            public Node getRightChild() {
                return rightChild;
            }

            public void setRightChild(Node rightChild) {
                this.rightChild = rightChild;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "value=" + value +
                        ", color=" + color +
                        '}';
            }
        }
    }

