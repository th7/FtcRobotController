# Things to Know
This document provides some terms that it will be helpful to memorize and code snippets that should be understood.

## Terms

<details>
<summary>symbol</summary>
An extremely generic term for a word in your code. Will most commonly be encountered in errors resembling "Cannot resolve symbol 'somethingInMyCode'".
</details>

<details>
<summary>scope</summary>
Generally refers to a section of code. The "scope" in which a piece of code is written determines which symbols can resolved in that code.
</details>

<details>
<summary>block</summary>
A section of code surrounded by `{}`. A block will affect scoping of local variables.
</details>

<details>
<summary>local variable</summary>
A variable that can only be used within the block in which it was created.
</details>

<details>
<summary>field</summary>
A field is defined within a class body. Fields be resolved anywhere within the class.
</details>

<details>
<summary>type</summary>
Describes what can be stored in a variable or field.

A type might be a primitive such as `int`, `float`, or `boolean`. 

A type might also be a class that comes from the Java language itself, a class from a library you've imported, or a class you created yourself.
</details>

<details>
<summary>access modifier</summary>
An access modifier describes where a class or method can be used by other code.
Most commonly, this will be `private` or `public`. A `private` method can only be called from within the class that defines the method. A `public` method can be called from outside the class.
</details>

<details>
<summary>constructor</summary>
A constructor is sort of like a special method that creates a new instance of a class. It uses the name of the class in its definition.
</details>

<details>
<summary>this</summary>
A keyword that refers to the instance of the class where the keyword is located. In most cases, it is implied and not needed, but sometimes it is necessary to differentiate fields from local variables or arguments.
</details>

## Snippets

---
```java
class Seuss {
    int thing1;
    double thing2 = 2d;
}
```
<details>

This is a class definition for a class named `Seuss`. `Seuss` has 2 fields. The field `thing1` is of type integer and does not have a default value. The field `thing2` is of type double and has a default value of 2.
</details>

---
```java
class Seuss {
    public final int thing1;
    private double thing2 = 2d;
}
```
<details>

Here we add modifiers to our fields. `thing1` gets an access modifier `public` to allow it to be used by code outside this class. It also gets the modifier `final` that means it cannot be changed. `thing2` gets the access modifier `private` which means it can only be used by code inside this class.
</details>

---
```java
class Seuss {
    public final int thing1;
    private double thing2 = 2d;
    
    Seuss(int thing1) {
        this.thing1 = thing1;
    }
}
```
<details>

This block adds a constructor. The constructor allows setting `thing1`, but it won't be possible to change it later due to the `final` modifier.
</details>

---
```java
class Seuss {
    public final int thing1;
    private double thing2 = 2d;

    Seuss(int thing1) {
        this.thing1 = thing1;
    }
    
    public void redFish() {
        thing2 += 1;
    }
}
```
<details>

This block adds a `public` method which returns nothing, is named `redFish`, and takes no arguments. It does, however, increase the value of the private field `thing2` by 1. This is an example of a `public` method can allow code outside the class to change a `private` field in limited ways.
</details>

---
```java
class Seuss {
    public final int thing1;
    private double thing2 = 2d;

    Seuss(int thing1) {
        this.thing1 = thing1;
    }

    public void redFish() {
        thing2 += 1;
    }
    
    public double thingsPlus(int thing3) {
        return thing1 + thing2 + thing3;
    }
}
```
<details>

Here we add a public method which returns a double named `thingsPlus` that takes an integer argument that it refers to as `thing3`. The method adds all the things together and returns them.
</details>