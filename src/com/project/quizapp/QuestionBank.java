package com.project.quizapp;

import java.util.ArrayList;

public class QuestionBank {

    public ArrayList<Question> generateAllQuestions() {
        ArrayList<Question> allQuestions = new ArrayList<>();

        allQuestions.addAll(createJavaQuestions());
        allQuestions.addAll(createBiologyQuestions());
        allQuestions.addAll(createEnglishQuestions());
        allQuestions.addAll(createJavaScriptQuestions());
        allQuestions.addAll(createPythonQuestions());
        allQuestions.addAll(createMathematicsQuestions());
        allQuestions.addAll(createHistoryQuestions());
        allQuestions.addAll(createScienceQuestions());
        allQuestions.addAll(createGeographyQuestions());

        return allQuestions;
    }

    ArrayList<Question> createJavaQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT001_Q001", "Which OOP concept allows an object to take many forms?",
                new String[]{"Abstraction", "Polymorphism", "Inheritance", "Composition"}, 1, "CAT001", "HARD", 10));

        questions.add(new Question("CAT001_Q002", "Which keyword is used to create an object in Java?",
                new String[]{"create", "alloc", "new", "make"}, 2, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q003", "Which keyword is used to call a superclass constructor?",
                new String[]{"base", "super", "parent", "this"}, 1, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q004", "Which of the following best defines inheritance?",
                new String[]{"Creating a new class using properties of an existing class",
                        "Copying code from one class to another", "Combining multiple classes",
                        "Using methods from another package"}, 0, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q005", "Which OOP feature promotes code reusability?",
                new String[]{"Abstraction", "Inheritance", "Polymorphism", "Composition"}, 1, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q006", "What is method overloading in Java?",
                new String[]{"Same method name with different parameters", "Same method name and parameters",
                        "Different methods in subclasses", "Overriding superclass methods"}, 0, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q007", "Which OOP concept hides internal implementation but shows only functionality?",
                new String[]{"Abstraction", "Polymorphism", "Inheritance", "Composition"}, 0, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q008", "Which of the following cannot be instantiated directly?",
                new String[]{"Abstract class", "Concrete class", "Subclass", "Final class"}, 0, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q009", "Which OOP concept allows different classes to define methods with the same name?",
                new String[]{"Inheritance", "Overloading", "Polymorphism", "Composition"}, 2, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q010", "Which method is automatically called when an object is destroyed?",
                new String[]{"delete()", "finalize()", "destroy()", "remove()"}, 1, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q011", "What is true about interfaces and abstract classes?",
                new String[]{"Abstract classes can have constructors, interfaces cannot",
                        "Interfaces can have instance variables", "Interfaces can extend classes",
                        "Interfaces cannot be inherited"}, 0, "CAT001", "HARD", 10));

        questions.add(new Question("CAT001_Q012", "What is the main advantage of abstraction in OOP?",
                new String[]{"Improves runtime speed", "Hides implementation details and shows essential features",
                        "Prevents inheritance", "Encourages composition"}, 1, "CAT001", "HARD", 10));

        questions.add(new Question("CAT001_Q013", "Which OOP feature allows the same method to behave differently at runtime?",
                new String[]{"Method Overloading", "Method Overriding", "Constructor Chaining", "Static Binding"},
                1, "CAT001", "HARD", 10));

        questions.add(new Question("CAT001_Q014", "Which of the following cannot be overridden in Java?",
                new String[]{"Static methods", "Instance methods", "Abstract methods", "Final methods"}, 0, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q015", "Which OOP concept allows subclasses to provide a specific implementation of a method?",
                new String[]{"Method Overloading", "Method Overriding", "Inheritance", "Abstraction"}, 1, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q016", "What is the correct statement about abstract classes?",
                new String[]{"They must have all abstract methods", "They can contain both abstract and concrete methods",
                        "They cannot extend another class", "They cannot be inherited"}, 1, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q017", "Which feature allows runtime method binding in Java?",
                new String[]{"Overloading", "Method Overriding", "Constructor chaining", "Static binding"},
                1, "CAT001", "HARD", 10));

        questions.add(new Question("CAT001_Q018", "What is an object in Java?",
                new String[]{"A function", "An instance of a class", "A data type", "A file in memory"}, 1, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q019", "Which OOP concept allows defining a method in an interface without implementation?",
                new String[]{"Abstraction", "Inheritance", "Polymorphism", "Aggregation"}, 0, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q020", "What keyword is used to prevent inheritance in Java?",
                new String[]{"static", "final", "const", "super"}, 1, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q021", "Which class is the root of the class hierarchy in Java?",
                new String[]{"Main", "Object", "System", "Base"}, 1, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q022", "Which OOP principle allows creating a new class that uses existing class features?",
                new String[]{"Inheritance", "Abstraction", "Aggregation", "Polymorphism"}, 0, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q023", "Which loop is guaranteed to execute at least once?",
                new String[]{"for", "while", "do-while", "foreach"}, 2, "CAT001", "EASY", 5));

        questions.add(new Question("CAT001_Q024", "Which statement is true about method overriding?",
                new String[]{"Must have same name and parameters", "Can have different parameters", "Must be static", "None"},
                0, "CAT001", "MEDIUM", 8));

        questions.add(new Question("CAT001_Q025", "Which OOP concept is implemented using interfaces in Java?",
                new String[]{"Polymorphism", "Abstraction", "Inheritance", "Composition"}, 1, "CAT001", "MEDIUM", 8));

        return questions;
    }

    ArrayList<Question> createBiologyQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT002_Q001", "What is the powerhouse of the cell?",
                new String[]{"Nucleus", "Rquestionsibosome", "Mitochondria", "Cell Wall"}, 2, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q002", "Which part of the cell controls all its activities?",
                new String[]{"Mitochondria", "Nucleus", "Ribosome", "Cytoplasm"}, 1, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q003", "Which organ removes waste from the blood?",
                new String[]{"Liver", "Stomach", "Kidneys", "Heart"}, 2, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q004", "What gas do plants absorb from the atmosphere?",
                new String[]{"Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"}, 2, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q005", "Which organ in humans produces insulin?",
                new String[]{"Liver", "Pancreas", "Gallbladder", "Kidney"}, 1, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q006", "What is the basic unit of life?",
                new String[]{"Tissue", "Organ", "Cell", "Organism"}, 2, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q007", "What pigment gives plants their green color?",
                new String[]{"Carotene", "Chlorophyll", "Xanthophyll", "Anthocyanin"}, 1, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q008", "Which organ is responsible for pumping blood?",
                new String[]{"Lungs", "Brain", "Heart", "Liver"}, 2, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q009", "What type of acid is found in DNA?",
                new String[]{"Amino acid", "Fatty acid", "Nucleic acid", "Ascorbic acid"}, 2, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q010", "What is the largest organ in the human body?",
                new String[]{"Brain", "Heart", "Skin", "Lungs"}, 2, "CAT002", "EASY", 5));

        questions.add(new Question("CAT002_Q011", "Which structure regulates what enters and leaves the cell?",
                new String[]{"Cell membrane", "Cytoplasm", "Cell wall", "Golgi apparatus"}, 0, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q012", "Which blood cells help in clotting?",
                new String[]{"Red Blood Cells", "White Blood Cells", "Platelets", "Plasma"}, 2, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q013", "What is the main function of ribosomes?",
                new String[]{"Protein synthesis", "Energy production", "Storage", "Transport"}, 0, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q014", "Which part of the brain controls balance and coordination?",
                new String[]{"Cerebrum", "Cerebellum", "Medulla", "Hypothalamus"}, 1, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q015", "Which organ is primarily responsible for detoxifying harmful substances?",
                new String[]{"Kidneys", "Liver", "Lungs", "Pancreas"}, 1, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q016", "What is the process by which plants make their own food?",
                new String[]{"Respiration", "Photosynthesis", "Transpiration", "Germination"}, 1, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q017", "Which gas do humans exhale during respiration?",
                new String[]{"Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen"}, 1, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q018", "Which system transports nutrients and oxygen in the body?",
                new String[]{"Respiratory system", "Circulatory system", "Digestive system", "Nervous system"}, 1, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q019", "What is the function of the large intestine?",
                new String[]{"Absorbs water", "Produces bile", "Stores food", "Breaks down protein"}, 0, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q020", "Which organ filters blood to form urine?",
                new String[]{"Heart", "Liver", "Kidneys", "Lungs"}, 2, "CAT002", "MEDIUM", 8));

        questions.add(new Question("CAT002_Q021", "What process divides a cell into two identical daughter cells?",
                new String[]{"Mitosis", "Meiosis", "Fertilization", "Binary fission"}, 0, "CAT002", "HARD", 10));

        questions.add(new Question("CAT002_Q022", "Which organelle contains genetic material in eukaryotic cells?",
                new String[]{"Mitochondria", "Nucleus", "Chloroplast", "Golgi apparatus"}, 1, "CAT002", "HARD", 10));

        questions.add(new Question("CAT002_Q023", "Which process produces gametes in animals?",
                new String[]{"Mitosis", "Meiosis", "Fission", "Spore formation"}, 1, "CAT002", "HARD", 10));

        questions.add(new Question("CAT002_Q024", "What is the role of chloroplasts in plant cells?",
                new String[]{"Store starch", "Perform photosynthesis", "Regulate temperature", "Transport nutrients"}, 1, "CAT002", "HARD", 10));

        questions.add(new Question("CAT002_Q025", "Which molecule carries genetic information?",
                new String[]{"RNA", "DNA", "Protein", "Lipid"}, 1, "CAT002", "HARD", 10));

        return questions;
    }

    ArrayList<Question> createEnglishQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT003_Q001", "Which of the following is an adverb?",
                new String[]{"Beautiful", "Running", "Quickly", "House"}, 2, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q002", "The word for a group of lions is a ____.",
                new String[]{"Herd", "Pack", "Pride", "Flock"}, 2, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q003", "Identify the subject in the sentence: 'The old car broke down.'",
                new String[]{"The old car", "broke down", "car", "The old"}, 0, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q004", "What is the past tense of 'sing'?",
                new String[]{"singed", "sang", "sung", "singing"}, 1, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q005", "Which punctuation mark is used to show possession?",
                new String[]{"Comma", "Semicolon", "Apostrophe", "Colon"}, 2, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q006", "What is the antonym of 'Generous'?",
                new String[]{"Kind", "Selfish", "Happy", "Polite"}, 1, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q007", "A 'simile' is a figure of speech that compares two things using 'like' or 'as'.",
                new String[]{"True", "False"}, 0, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q008", "Which word is a preposition?",
                new String[]{"and", "but", "under", "quickly"}, 2, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q009", "What is the plural of 'child'?",
                new String[]{"childs", "childrens", "children", "child's"}, 2, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q010", "Which type of sentence expresses a strong emotion and ends with an exclamation mark?",
                new String[]{"Declarative", "Interrogative", "Exclamatory", "Imperative"}, 2, "CAT003", "EASY", 5));

        questions.add(new Question("CAT003_Q011", "Which part of speech names a person, place, or thing?",
                new String[]{"Adjective", "Verb", "Noun", "Adverb"}, 2, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q012", "Which of the following is a synonym for 'Brave'?",
                new String[]{"Cowardly", "Courageous", "Afraid", "Timid"}, 1, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q013", "Choose the correct article: 'I saw ___ elephant in the zoo.'",
                new String[]{"a", "an", "the", "no article"}, 1, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q014", "Identify the adjective in the sentence: 'She wore a beautiful dress.'",
                new String[]{"She", "wore", "beautiful", "dress"}, 2, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q015", "Which of these sentences is in the passive voice?",
                new String[]{"The boy kicked the ball.", "The ball was kicked by the boy.", "The boy is kicking the ball.", "Kick the ball!"}, 1, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q016", "What is the correct comparative form of 'good'?",
                new String[]{"gooder", "better", "more good", "best"}, 1, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q017", "Which sentence is in the future tense?",
                new String[]{"She goes to school.", "She is going to school.", "She went to school.", "She will go to school."}, 3, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q018", "What is the correct plural of 'mouse'?",
                new String[]{"mouses", "mices", "mouse", "mice"}, 3, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q019", "Which conjunction best completes the sentence: 'I wanted to go out, ___ it was raining.'",
                new String[]{"and", "but", "because", "so"}, 1, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q020", "Identify the type of phrase in: 'After the show, we went home.'",
                new String[]{"Noun phrase", "Adjective phrase", "Prepositional phrase", "Verb phrase"}, 2, "CAT003", "MEDIUM", 8));

        questions.add(new Question("CAT003_Q021", "Which sentence uses the correct form of the verb 'to be'?",
                new String[]{"He are my friend.", "He is my friend.", "He am my friend.", "He be my friend."}, 1, "CAT003", "HARD", 10));

        questions.add(new Question("CAT003_Q022", "Identify the figure of speech in: 'The world is a stage.'",
                new String[]{"Simile", "Metaphor", "Personification", "Hyperbole"}, 1, "CAT003", "HARD", 10));

        questions.add(new Question("CAT003_Q023", "Which sentence has a grammatical error?",
                new String[]{"She don’t like tea.", "She doesn’t like tea.", "They play football.", "I am reading."}, 0, "CAT003", "HARD", 10));

        questions.add(new Question("CAT003_Q024", "Which of the following words is a homophone of 'flour'?",
                new String[]{"flower", "floor", "floury", "flow"}, 0, "CAT003", "HARD", 10));

        questions.add(new Question("CAT003_Q025", "Which sentence correctly uses an idiom?",
                new String[]{"He let the cat out of the bag.", "He released the bag’s cat.", "He is chasing cats.", "He caught the bag."}, 0, "CAT003", "HARD", 10));

        return questions;
    }

    ArrayList<Question> createJavaScriptQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT004_Q001", "Which of the following correctly declares a variable in JavaScript?",
                new String[]{"var x = 10;", "int x = 10;", "let x == 10;", "declare x = 10;"}, 0, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q002", "Which of the following is NOT a JavaScript data type?",
                new String[]{"String", "Boolean", "Character", "Undefined"}, 2, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q003", "What is the output of typeof null?",
                new String[]{"null", "undefined", "object", "boolean"}, 2, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q004", "Which method is used to print something to the console?",
                new String[]{"console.log()", "print()", "echo()", "log.console()"}, 0, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q005", "Which of the following will return NaN?",
                new String[]{"Number('10')", "parseInt('123abc')", "parseFloat('3.14')", "Number(true)"}, 1, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q006", "Which keyword is used to declare a constant in JavaScript?",
                new String[]{"let", "const", "var", "static"}, 1, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q007", "What is the result of '2' + 2 in JavaScript?",
                new String[]{"4", "'22'", "NaN", "undefined"}, 1, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q008", "Which symbol is used for single-line comments?",
                new String[]{"#", "//", "/*", "--"}, 1, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q009", "Which method adds a new element to the end of an array and returns the new length?",
                new String[]{"append()", "push()", "concat()", "add()"}, 1, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q010", "Which of the following statements about 'let' is TRUE?",
                new String[]{"It is function-scoped.", "It is block-scoped.", "It redeclares like var.", "It creates global variables."}, 1, "CAT004", "EASY", 5));

        questions.add(new Question("CAT004_Q011", "What will 'arr.pop()' return when called on [10, 20, 30]?",
                new String[]{"30", "2", "[10, 20]", "undefined"}, 0, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q012", "Which method returns a shallow copy of a portion of an array?",
                new String[]{"splice()", "slice()", "split()", "join()"}, 1, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q013", "What will be the return type of Array.push()?",
                new String[]{"Array", "Number", "Boolean", "Undefined"}, 1, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q014", "What is the return value of 'Array.prototype.unshift()'?",
                new String[]{"The added element", "The new array", "The new length", "undefined"}, 2, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q015", "Which of the following correctly creates a Promise object?",
                new String[]{"let p = new Promise();", "let p = Promise.create();", "let p = new Promise((res, rej) => {});", "let p = new promise()"}, 2, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q016", "Which of these array methods changes the original array?",
                new String[]{"map()", "filter()", "slice()", "splice()"}, 3, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q017", "What will 'typeof []' return?",
                new String[]{"array", "object", "list", "undefined"}, 1, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q018", "Which statement correctly defines an arrow function that returns x * 2?",
                new String[]{"x => {x * 2}", "x => x * 2", "(x) -> x * 2", "function(x) => x * 2"}, 1, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q019", "Which object method is used to merge multiple objects into one?",
                new String[]{"Object.create()", "Object.assign()", "Object.merge()", "Object.copy()"}, 1, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q020", "Which loop guarantees at least one execution?",
                new String[]{"for", "while", "do...while", "forEach"}, 2, "CAT004", "MEDIUM", 8));

        questions.add(new Question("CAT004_Q021", "What is the output of 'Promise.resolve(5).then(x => x * 2)'?",
                new String[]{"5", "10", "Promise {10}", "undefined"}, 2, "CAT004", "HARD", 10));

        questions.add(new Question("CAT004_Q022", "What will 'console.log([] == false)' print?",
                new String[]{"true", "false", "undefined", "error"}, 0, "CAT004", "HARD", 10));

        questions.add(new Question("CAT004_Q023", "What is the output of the expression 'typeof NaN'?",
                new String[]{"NaN", "number", "undefined", "object"}, 1, "CAT004", "HARD", 10));

        questions.add(new Question("CAT004_Q024", "Which function is used to schedule code execution after a delay?",
                new String[]{"setDelay()", "setTimeout()", "wait()", "sleep()"}, 1, "CAT004", "HARD", 10));

        questions.add(new Question("CAT004_Q025", "What is the value of 'this' inside a regular function called in strict mode?",
                new String[]{"window", "undefined", "global", "null"}, 1, "CAT004", "HARD", 10));

        return questions;
    }

    private ArrayList<Question> createPythonQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT005_Q001", "Which keyword is used to define a function in Python?",
                new String[]{"function", "def", "func", "define"}, 1, "CAT005", "EASY", 5));
        questions.add(new Question("CAT005_Q002", "What is the correct way to create a list in Python?",
                new String[]{"list = (1, 2, 3)", "list = [1, 2, 3]", "list = {1, 2, 3}", "list = <1, 2, 3>"}, 1, "CAT005", "EASY", 5));
        questions.add(new Question("CAT005_Q003", "Which of the following is used for comments in Python?",
                new String[]{"//", "/* */", "#", "--"}, 2, "CAT005", "EASY", 5));
        questions.add(new Question("CAT005_Q004", "What does the 'len()' function do?",
                new String[]{"Returns length of object", "Returns type", "Deletes object", "Copies object"}, 0, "CAT005", "EASY", 5));
        questions.add(new Question("CAT005_Q005", "Which method adds an element at the end of a list?",
                new String[]{"add()", "append()", "insert()", "extend()"}, 1, "CAT005", "EASY", 5));
        questions.add(new Question("CAT005_Q006", "What is the output of 'print(type([]))'?",
                new String[]{"<class 'array'>", "<class 'list'>", "<class 'tuple'>", "<class 'dict'>"}, 1, "CAT005", "MEDIUM", 8));
        questions.add(new Question("CAT005_Q007", "Which keyword is used for exception handling?",
                new String[]{"catch", "except", "error", "handle"}, 1, "CAT005", "MEDIUM", 8));
        questions.add(new Question("CAT005_Q008", "What does '==' operator check in Python?",
                new String[]{"Value equality", "Reference equality", "Type equality", "Memory address"}, 0, "CAT005", "MEDIUM", 8));
        questions.add(new Question("CAT005_Q009", "Which of these is NOT a valid Python data type?",
                new String[]{"int", "float", "char", "bool"}, 2, "CAT005", "HARD", 10));
        questions.add(new Question("CAT005_Q010", "What is a lambda function in Python?",
                new String[]{"Named function", "Anonymous function", "Class method", "Built-in function"}, 1, "CAT005", "HARD", 10));

        return questions;
    }

    private ArrayList<Question> createMathematicsQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT006_Q001", "What is 15 × 12?",
                new String[]{"160", "170", "180", "190"}, 2, "CAT006", "EASY", 5));
        questions.add(new Question("CAT006_Q002", "What is the value of π (pi) approximately?",
                new String[]{"3.14", "2.71", "1.41", "1.73"}, 0, "CAT006", "EASY", 5));
        questions.add(new Question("CAT006_Q003", "What is the square root of 144?",
                new String[]{"10", "11", "12", "13"}, 2, "CAT006", "EASY", 5));
        questions.add(new Question("CAT006_Q004", "What is 25% of 200?",
                new String[]{"25", "40", "50", "75"}, 2, "CAT006", "EASY", 5));
        questions.add(new Question("CAT006_Q005", "What is the sum of angles in a triangle?",
                new String[]{"90°", "180°", "270°", "360°"}, 1, "CAT006", "EASY", 5));
        questions.add(new Question("CAT006_Q006", "What is the formula for the area of a circle?",
                new String[]{"πr", "πr²", "2πr", "πd"}, 1, "CAT006", "MEDIUM", 8));
        questions.add(new Question("CAT006_Q007", "What is the value of 2³ + 3²?",
                new String[]{"13", "15", "17", "19"}, 2, "CAT006", "MEDIUM", 8));
        questions.add(new Question("CAT006_Q008", "In algebra, what is the value of x in: 2x + 5 = 15?",
                new String[]{"4", "5", "6", "7"}, 1, "CAT006", "MEDIUM", 8));
        questions.add(new Question("CAT006_Q009", "What is the derivative of x² with respect to x?",
                new String[]{"x", "2x", "x²", "2x²"}, 1, "CAT006", "HARD", 10));
        questions.add(new Question("CAT006_Q010", "What is the Pythagorean theorem?",
                new String[]{"a + b = c", "a² + b² = c²", "a × b = c", "a/b = c"}, 1, "CAT006", "HARD", 10));

        return questions;
    }

    // CATEGORY 7: History (NEW)
    private ArrayList<Question> createHistoryQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT007_Q001", "In which year did World War II end?",
                new String[]{"1943", "1944", "1945", "1946"}, 2, "CAT007", "EASY", 5));
        questions.add(new Question("CAT007_Q002", "Who was the first President of the United States?",
                new String[]{"Thomas Jefferson", "George Washington", "Abraham Lincoln", "John Adams"}, 1, "CAT007", "EASY", 5));
        questions.add(new Question("CAT007_Q003", "Which empire built the Colosseum?",
                new String[]{"Greek", "Roman", "Egyptian", "Persian"}, 1, "CAT007", "EASY", 5));
        questions.add(new Question("CAT007_Q004", "What year did Christopher Columbus reach the Americas?",
                new String[]{"1492", "1500", "1520", "1450"}, 0, "CAT007", "EASY", 5));
        questions.add(new Question("CAT007_Q005", "Who was known as the 'Iron Lady'?",
                new String[]{"Angela Merkel", "Indira Gandhi", "Margaret Thatcher", "Golda Meir"}, 2, "CAT007", "MEDIUM", 8));
        questions.add(new Question("CAT007_Q006", "Which ancient wonder is the only one still standing?",
                new String[]{"Colossus of Rhodes", "Hanging Gardens", "Great Pyramid of Giza", "Lighthouse of Alexandria"}, 2, "CAT007", "MEDIUM", 8));
        questions.add(new Question("CAT007_Q007", "What was the name of the ship that brought Pilgrims to America?",
                new String[]{"Santa Maria", "Mayflower", "Endeavour", "Victory"}, 1, "CAT007", "MEDIUM", 8));
        questions.add(new Question("CAT007_Q008", "Who wrote the Declaration of Independence?",
                new String[]{"George Washington", "Benjamin Franklin", "Thomas Jefferson", "John Adams"}, 2, "CAT007", "HARD", 10));
        questions.add(new Question("CAT007_Q009", "What was the name of the first human civilization?",
                new String[]{"Roman", "Greek", "Egyptian", "Sumerian"}, 3, "CAT007", "HARD", 10));
        questions.add(new Question("CAT007_Q010", "In which year did the Berlin Wall fall?",
                new String[]{"1987", "1988", "1989", "1990"}, 2, "CAT007", "HARD", 10));

        return questions;
    }

    // CATEGORY 8: Science (NEW)
    private ArrayList<Question> createScienceQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT008_Q001", "What is the chemical symbol for water?",
                new String[]{"O2", "H2O", "CO2", "HO"}, 1, "CAT008", "EASY", 5));
        questions.add(new Question("CAT008_Q002", "What planet is known as the Red Planet?",
                new String[]{"Venus", "Jupiter", "Mars", "Saturn"}, 2, "CAT008", "EASY", 5));
        questions.add(new Question("CAT008_Q003", "What force keeps us on the ground?",
                new String[]{"Magnetism", "Gravity", "Friction", "Pressure"}, 1, "CAT008", "EASY", 5));
        questions.add(new Question("CAT008_Q004", "What is the speed of light?",
                new String[]{"300,000 km/s", "150,000 km/s", "500,000 km/s", "100,000 km/s"}, 0, "CAT008", "EASY", 5));
        questions.add(new Question("CAT008_Q005", "What is the chemical symbol for gold?",
                new String[]{"Go", "Gd", "Au", "Ag"}, 2, "CAT008", "MEDIUM", 8));
        questions.add(new Question("CAT008_Q006", "What is the largest organ in the human body?",
                new String[]{"Heart", "Brain", "Liver", "Skin"}, 3, "CAT008", "MEDIUM", 8));
        questions.add(new Question("CAT008_Q007", "What type of energy does the sun produce?",
                new String[]{"Chemical", "Nuclear", "Electrical", "Mechanical"}, 1, "CAT008", "MEDIUM", 8));
        questions.add(new Question("CAT008_Q008", "What is the smallest unit of matter?",
                new String[]{"Molecule", "Atom", "Electron", "Proton"}, 1, "CAT008", "HARD", 10));
        questions.add(new Question("CAT008_Q009", "What is the pH of a neutral solution?",
                new String[]{"5", "6", "7", "8"}, 2, "CAT008", "HARD", 10));
        questions.add(new Question("CAT008_Q010", "What is Newton's First Law of Motion?",
                new String[]{"F = ma", "Action-Reaction", "Inertia", "Energy Conservation"}, 2, "CAT008", "HARD", 10));

        return questions;
    }

    // CATEGORY 9: Geography (NEW)
    private ArrayList<Question> createGeographyQuestions() {
        ArrayList<Question> questions = new ArrayList<>();

        questions.add(new Question("CAT009_Q001", "What is the capital of France?",
                new String[]{"London", "Berlin", "Paris", "Rome"}, 2, "CAT009", "EASY", 5));
        questions.add(new Question("CAT009_Q002", "Which is the largest ocean on Earth?",
                new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}, 3, "CAT009", "EASY", 5));
        questions.add(new Question("CAT009_Q003", "How many continents are there?",
                new String[]{"5", "6", "7", "8"}, 2, "CAT009", "EASY", 5));
        questions.add(new Question("CAT009_Q004", "What is the longest river in the world?",
                new String[]{"Amazon", "Nile", "Yangtze", "Mississippi"}, 1, "CAT009", "EASY", 5));
        questions.add(new Question("CAT009_Q005", "Which country has the largest population?",
                new String[]{"India", "USA", "China", "Indonesia"}, 2, "CAT009", "EASY", 5));
        questions.add(new Question("CAT009_Q006", "What is the tallest mountain in the world?",
                new String[]{"K2", "Everest", "Kilimanjaro", "Denali"}, 1, "CAT009", "MEDIUM", 8));
        questions.add(new Question("CAT009_Q007", "Which desert is the largest in the world?",
                new String[]{"Sahara", "Arabian", "Gobi", "Kalahari"}, 0, "CAT009", "MEDIUM", 8));
        questions.add(new Question("CAT009_Q008", "What is the smallest country in the world?",
                new String[]{"Monaco", "Vatican City", "San Marino", "Liechtenstein"}, 1, "CAT009", "MEDIUM", 8));
        questions.add(new Question("CAT009_Q009", "Which line divides the Earth into Northern and Southern hemispheres?",
                new String[]{"Prime Meridian", "Tropic of Cancer", "Equator", "Arctic Circle"}, 2, "CAT009", "HARD", 10));
        questions.add(new Question("CAT009_Q010", "What is the capital of Australia?",
                new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, 2, "CAT009", "HARD", 10));

        return questions;
    }
}
