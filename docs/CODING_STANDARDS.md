# Coding Standards

## Table of Contents
- [Coding Standards](#coding-standards)
  - [Table of Contents](#table-of-contents)
  - [General Principles](#general-principles)
  - [File Naming Conventions](#file-naming-conventions)
  - [Class and Interface Naming](#class-and-interface-naming)
  - [Method and Variable Naming](#method-and-variable-naming)
  - [Formatting](#formatting)
    - [Example:](#example)
  - [Commit Messages](#commit-messages)

## General Principles
- Write clean, readable, and maintainable code.
- Follow the SOLID principles.
- Adhere to the DRY (Don't Repeat Yourself) principle.
- Prefer composition over inheritance.
- Ensure that the code is well-documented and easy to understand.

## File Naming Conventions
- Source file names should be in camel case and match the public class name within the file. Example: `MyClass.java`
- Test file names should be the same as the class they are testing with the suffix `Test`. Example: `MyClassTest.java`

## Class and Interface Naming
- Class names should be nouns, written in UpperCamelCase. Example: `CustomerOrder`
- Interface names should be adjectives, also in UpperCamelCase. Example: `Runnable`, `Serializable`
- Avoid abbreviations and acronyms unless they are widely accepted. Example: `HttpRequest` instead of `HTTPReq`

## Method and Variable Naming
- Method names should be verbs, written in lowerCamelCase. Example: `calculateTotal()`
- Variable names should be written in lowerCamelCase or snake case. Example: `totalAmount` or `total_amount`
- Constant names should be written in all uppercase with words separated by underscores. Example: `MAX_VALUE`

## Formatting
- **Use 4 spaces for indentation. Do not use tabs.**
- Limit lines to 100 characters.
- Use blank lines to separate logical sections of the code.
- Place the opening brace `{` at the end of the line declaring the class, method, or block, and the closing brace `}` on a new line.

### Example:
```java
public class MyClass {
    private int myVariable;

    public MyClass(int myVariable) {
        this.myVariable = myVariable;
    }

    public int getMyVariable() {
        return myVariable;
    }

    public void setMyVariable(int myVariable) {
        this.myVariable = myVariable;
    }
}
```

## Commit Messages

1. Separate subject from body with a blank line
1. Limit the subject line to 50 characters
1. Capitalize the subject line
1. Do not end the subject line with a period
1. Use the imperative mood in the subject line (example: "Fix networking issue")
1. Wrap the body at about 72 characters
1. Use the body to explain **why**, *not what and how* (the code shows that!)
1. If applicable, prefix the title with the relevant component name. (examples: "[Docs] Fix typo", "[Profile] Fix missing avatar")

```
[TAG] Short summary of changes in 50 chars or less

Add a more detailed explanation here, if necessary. Possibly give 
some background about the issue being fixed, etc. The body of the 
commit message can be several paragraphs. Further paragraphs come 
after blank lines and please do proper word-wrap.

Wrap it to about 72 characters or so. In some contexts, 
the first line is treated as the subject of the commit and the 
rest of the text as the body. The blank line separating the summary 
from the body is critical (unless you omit the body entirely); 
various tools like `log`, `shortlog` and `rebase` can get confused 
if you run the two together.

Explain the problem that this commit is solving. Focus on why you
are making this change as opposed to how or what. The code explains 
how or what. Reviewers and your future self can read the patch, 
but might not understand why a particular solution was implemented.
Are there side effects or other unintuitive consequences of this
change? Here's the place to explain them.

 - Bullet points are okay, too

 - A hyphen or asterisk should be used for the bullet, preceded
   by a single space, with blank lines in between
```