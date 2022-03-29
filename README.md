# Code Questions

Contained in this project are coding question's I've come across.  I've found these problems particularly interesting
because their problem statements are clear and each have interesting edge cases. 

The solutions in this project are intentionally verbose and structured as if they were part of a real project. Many of 
these solutions can be collapsed into a single file, but structuring in this way helps drive home some of the design
choices and implementations.

## BigInteger

This problem seeks to replace the `add` function of Java's [BigInteger](https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html)
class. On the surface the solution seems straight forward, but there is a particular edge case that leads to an 
interesting implementation.

Many implementations will start by representing the BigInteger as a string of digits or a list of digits. The `add` 
method of these implementations will use the `.size` or `.length` methods to figure out where to start or when to end
an iteration: 

```java
String a = "123";
String b = "456";
for (int i = 0; i < Math.max(a.length, b.length); i++) {
    ...
}
```

With sufficiently large integers it's possible that the results of `.length` and `size` would be greater than Java's
`Integer.MAX_INT` which would result in the value will be an overflown integer. Representing the BigInteger as a 
LinkedList can avoid these issues, as is proposed in `BigInteger.java`.

Of course this solution assumes infinite memory. If memory were constrained then a potential implementation may involve 
streaming data to/from files...which of course assumes infinite disk space. Constraining both memory and disk would 
be an interesting design involving distributed computing. 

## FindFile

This is one of my favorite problems because the solution is elegant, easy to grok, and easily extensible. The root of 
the problem is to "find files that meet certain criteria." When starting with 1 example the problem seems simple -- 
simply traverse a directory structure and find all files that match the provided criteria. However, the problem is 
deeper than just 1 evaluation criteria. 

What if we wanted to add a second, third, fourth, fith, or Nth criteria? What if I wanted to evaluate against multiple 
criteria using `and` and `or`operators? The problem quickly more complicated as new requirements are added.  The 
solution is to use inheritance to create a `Matcher` interface. 

```java
public interface Matcher {
    boolean matches(File file);
}
```

Creating this "matcher" abstraction greatly simplifies the problem because it distills the solution into composable 
parts. Implementations of this interface can be found in the `findfile.matchers` package and the main "find file" 
algorithm is implemented in `FindFiles.java`. Below is a demonstration of finding all "monthly_report.csv" files that 
have a size >= 1MB.  

```java
Matcher fileNameMatcher = new FileNameMatcher("monthly_report.csv", NameOperator.EQUALS);
Matcher fileSizeMatcher = new FileSizeMatcher(1_000_000, SizeOperator.GTEQ);
Matcher criteria = new AndMatcher(Arrays.asList(fileNameMatcher, fileSizeMatcher));

File file = new File("/path/to/my/files/");
FindFiles.find(file, criteria);
```

## OrgChart

This problem is short and sweet -- the root of the problem is to build an "org chart" that maps the relationship
between employees and bosses. Included in the implementation are a few functions that force an interesting tree 
implementation; particularly calculating the personnel cost of an entire organization. 

A basic implementation may include traversing the tree and summing all salaries under a specific root node (i.e. CEO).
If we were to assume that the organization were sufficiently wide or sufficiently deep, then it would be reasonable to
assume that re-computing an organization's cost could be time intensive since the implementation has an O(N) runtime 
complexity, where N is the number of employees. 

```java
public double orgCost() {
    double sumSalaries = this.salary;
    for (Employee r : this.reports) {
        sumSalaries += r.orgCost();
    }
}
```

Recognizing that organization costs can be updated and persisted upon employee insertion or removal can reduce query 
latency to O(1), but increases insertion/removal latency from O(1) to O(MxW) where M is the distance between the highest
and lowest employee and W is the average number of direct reports. 

```java
public void add(Employee employee) {
    ...
    this.updateOrgCost();
}

private void updateOrgCost() {
    double newCost = this.salary;
    for (Employee r : this.reports) {
        newCost += r.orgCost;
    }

    this.orgCost = newCost;
    if (this.boss != null) {
        this.boss.updateOrgCost();
    }
}
```

Recognizing that the organizational cost of adding/removing employees can be represented as adding/subtracting the 
employee's salary to current costs reduces query latency to O(1) and only increases insertion/removal latency to O(M) 
where M is the distance between the highest and lowest employee. This implementation can be found in `Employee.java`. 


