JC = javac
SRC_DIR = src
TEST_DIR = test
CLASSES_DIR = classes
DOC_DIR = docs

SOURCES = $(shell find $(SRC_DIR) -name "*.java")
TEST_SOURCES = $(shell find $(TEST_DIR) -name "*.java")

.PHONY: all classes jar tests runtests docs clean

all: classes

classes:
	mkdir -p $(CLASSES_DIR)
	$(JC) -d $(CLASSES_DIR) $(SOURCES)

jar: classes
	jar cvfe towerdefense-a-interactive.jar towerdefense.livrable.livrableFinale.towerdefenseAInteractive -C $(CLASSES_DIR) .
	jar cvfe towerdefense-b-interactive.jar towerdefense.livrable.livrableFinale.towerdefenseBInteractive -C $(CLASSES_DIR) .
	jar cvfe towerdefense-a-random.jar towerdefense.livrable.livrableFinale.towerdefenseARandom -C $(CLASSES_DIR) .
	jar cvfe towerdefense-b-random.jar towerdefense.livrable.livrableFinale.towerdefenseBRandom -C $(CLASSES_DIR) .

# Règle pour Linux / macOS (séparateur :)
tests: classes
	$(JC) -classpath lib/junit-console.jar:$(CLASSES_DIR) $(TEST_SOURCES) -d $(CLASSES_DIR)

runtests: tests	
	java -jar lib/junit-console.jar -classpath $(TEST_DIR):$(CLASSES_DIR) -scan-classpath

# Règle pour Windows (séparateur ;)
testsWindows: classes
	$(JC) -classpath "lib/junit-console.jar;$(CLASSES_DIR)" $(TEST_SOURCES) -d $(CLASSES_DIR)

runtestsWindows: testsWindows	
	java -jar lib/junit-console.jar -classpath "$(TEST_DIR);$(CLASSES_DIR)" -scan-classpath


doc: classes
	mkdir -p $(DOC_DIR)
	javadoc -sourcepath src -subpackages towerdefense -d $(DOC_DIR)

clean:
	rm -rf $(CLASSES_DIR) $(DOC_DIR) towerdefense-*.jar