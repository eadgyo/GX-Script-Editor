package org.eadge.model.frame;

import org.eadge.gxscript.data.compile.script.RawGXScript;
import org.eadge.gxscript.tools.check.ValidatorModel;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by eadgyo on 19/02/17.
 *
 * Model for script testing
 */
public class TestsModel extends AbstractListModel<TestsModel.Test>
{
    private OutputStream testStream;

    private boolean         isScriptIndependent = false;
    private boolean         canExportCompiled   = false;
    private ArrayList<Test> tests               = new ArrayList<>();

    public TestsModel()
    {
    }

    public void setTestStream(OutputStream testStream)
    {
        this.testStream = testStream;
    }

    /**
     * Check if the script needs inputs
     * @return true if the script needs inputs, false otherwise
     */
    public boolean isScriptIndependent()
    {
        return isScriptIndependent;
    }

    public void setScriptIndependent(boolean scriptIndependent)
    {
        isScriptIndependent = scriptIndependent;
    }

    public boolean isCanExportCompiled()
    {
        return canExportCompiled;
    }

    public void setCanExportCompiled(boolean canExportCompiled)
    {
        this.canExportCompiled = canExportCompiled;
    }

    public void addTest(String name, ValidatorModel validatorModel)
    {
        tests.add(new Test(name, validatorModel));
        int testIndex = tests.size() - 1;
        this.fireIntervalAdded(tests.get(testIndex), testIndex, testIndex);
    }

    /**
     * Change the test result at this index
     * @param testIndex index of the test result
     * @param result test result
     */
    public void setTestResult(int testIndex, boolean result)
    {
        tests.get(testIndex).result = result;
        this.fireContentsChanged(tests.get(testIndex), testIndex, testIndex);
    }

    /**
     * Change the result of the corresponding test
     * @param name name of the tests
     * @param result test result
     */
    public void setTestResult(String name, boolean result)
    {
        // Search for the corresponding test
        int testIndex = getTestIndex(name);

        // If test exists
        if (testIndex != -1)
        {
            setTestResult(testIndex, result);
        }
    }

    /**
     * Get the index of the registered tests
     * @param name name test
     * @return index of the test
     */
    public int getTestIndex(String name)
    {
        int testIndex = 0;
        while (testIndex < tests.size() && !tests.get(testIndex).name.equals(name))
        {
            testIndex++;
        }
        return testIndex < tests.size() ? testIndex : -1;
    }

    /**
     * Performs all tests
     * @param rawGXScript tested script
     */
    public int validateAll(RawGXScript rawGXScript)
    {
        int error = 0;
        for (int testIndex = 0; testIndex < tests.size(); testIndex++)
        {
            validate(testIndex, rawGXScript);
            if (!getResult(testIndex))
                error++;
        }
        return error;
    }

    public boolean getResult(int i)
    {
        return getElementAt(i).result;
    }

    /**
     * Perform test at selected index
     * @param testIndex test index
     * @param rawGXScript tested script
     */
    public void validate(int testIndex, RawGXScript rawGXScript)
    {
        tests.get(testIndex).validate(rawGXScript);
        this.fireContentsChanged(tests.get(testIndex), testIndex, testIndex);
    }

    public OutputStream getTestStream()
    {
        return testStream;
    }

    @Override
    public int getSize()
    {
        return tests.size();
    }

    @Override
    public Test getElementAt(int i)
    {
        return tests.get(i);
    }

    private class StringOutputString extends OutputStream
    {
        private StringBuilder string = new StringBuilder();
        @Override
        public void write(int b) throws IOException
        {
            this.string.append((char) b );
        }

        public String toString(){
            return this.string.toString();
        }
    }

    class Test
    {
        boolean result;
        String name;
        ValidatorModel validatorModel;

        Test(String name, ValidatorModel validatorModel)
        {
            this.name = name;
            this.validatorModel = validatorModel;
            this.result = false;
        }

        void validate(RawGXScript rawGXScript)
        {
            result = validatorModel.validate(rawGXScript);
        }

        @Override
        public String toString()
        {
            String res = result? "[ PASSED ]" : "[ FAILED ]";
            return res + "   " + name;
        }
    }
}
