package ooad.comet_tutors;

import java.util.List;

/**
 * Created by Dillon on 10/3/2014.
 */
public class ExpandListGroup {

    private String rootCourse;
    private ExpandListChild child;
    private List<String> courseNames;

    public ExpandListGroup(String rootCourse, List<String> courseNames)
    {
        this.rootCourse = rootCourse;
        this.courseNames = courseNames;
        child = new ExpandListChild(courseNames);
    }

    public void setRootCourse(String rc)
    {
        rootCourse = rc;
    }

    public void setChild(ExpandListChild ch)
    {
        child = ch;
    }

    public String getRootCourse()
    {
        return rootCourse;
    }

    public ExpandListChild getChild()
    {
        return child;
    }

    public List<String> getCourseNames()
    {
        return courseNames;
    }
}
