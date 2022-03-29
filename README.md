# Code Questions

Contained in this project are coding question's I've come across over the years.  These are the ones I found 
particularly interesting and useful for evaluations, since the problem statements are clear and there are multiple 
solutions that help level-set a candidate. 

The solutions in this project are intentionally verbose and structured as if this were a real project. Many of these 
solutions can be collapsed into a single file, but structuring  the code as if it were a project helps drive home 
implementation concepts/benefits. 

## BigInteger

This problem seeks to replace the `add` function of Java's [BigInteger](https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html)
class. On the surface the solution seems straight forward, but there is a particular edge case that leads to interesting 
solutions. 

Many implementations will start by representing the BigInteger as a string of digits or a list of digits. The `add` 
method of these implementations will use the `.size``.length` methods to figure out when to end iteration: 

```java
String a = ...;
String b = ...;
for (int i = 0; i < Math.max(a.length, b.length); i++) {
    ...
}
```

If we assume that the integer is so larger that `.length` or `.size` is larger than Java's `Integer.MAX_INT`, then the 
returned value will be an overflown integer. Representing  the BigInteger as a LinkedList can avoid these issues, as is 
proposed in BigInteger.java.

Of course this solution assumes infinite memory. If memory were constrained then a potential implementation can involve 
streaming data to/from files...which of course assumes infinite disk space. Constraining both memory and disk would 
be an interesting design relating to distributed computing. 

## FindFile

This is one of my favorite problems because the solution is elegant, easy to grok, and easily extensible. The root of 
the problem is to "find files that meet certain criteria." When starting with 1 example the problem seems simple -- 
simply traverse a directory structure and find all files that match the provided criteria. However, the problem is 
deeper than 1 criteria. 

In addition to a "file size" criteria, what if we wanted to add "file name" criteria. Or, "file modified date;" or "last 
modified by"; or "file contents contains"; what if I wanted to evaluate against multiple criteria using `and` and `or` 
operators? The problem quickly more complicated. 

The trick is to use inheritance to create a `Matcher` interface. 

```java
public interface Matcher {
    boolean matches(File file);
}
```

Creating this "matcher" abstraction greatly simplifies the problem because it distills the solution into composable 
parts. Implementations of this interface can be found in the `findfile.matchers` package. Below is a demonstration of 
finding all "monthly_report.csv" files that have a size >= 1MB.  

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

A basic implementation would include traversing the tree and summing all salaries under a specific root node (i.e. CEO).
If we were to assume that the organization were sufficiently wide or sufficiently deep, then it would be reasonable to
assume that re-computing an organization's cost could be time intensive since the implementation
has an O(N) runtime complexity, where N is the number of employees. 

*insert dfs algorithm*

Recognizing that organization costs can be updated and persisted upon employee insertion or removal reduces query 
latency to O(1), but increases insertion/removal latency from O(1) to O(MxW) where M is the distance between the highest
and lowest employee and M is the average number of direct reports. 

**insert code block here**

Recognizing that the organizational cost of adding and removing employees can be represented as additions/subtractions
reduces query latency to O(1) and only increases insertion/removal latency to O(M) where M is the distance between
the highest and lowest employee. This implementation can be found in `Employee.java`. 


