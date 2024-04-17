package org.example;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.*;

public class TestSamplerGui extends AbstractSamplerGui {
    @Override
    public String getLabelResource() {
        return "Test Sampler";
    }

    private final JTextField responseTime = new JTextField();
    private final JTextField label = new JTextField();
    private final JTextField responseCode = new JTextField();
    private final JCheckBox success = new JCheckBox("Success",true);

    private final JPanel createTestSamplerPanel() {
        JPanel mainPanel = new JPanel();

        mainPanel.add(responseTime);
        mainPanel.add(label);
        mainPanel.add(responseCode);
        mainPanel.add(success);

        return mainPanel;
    }

    public TestSamplerGui() {
        setLayout(new BorderLayout());
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        add(createTestSamplerPanel(),BorderLayout.CENTER);
    }

    @Override
    public String getStaticLabel() {
        return getLabelResource();
    }

    @Override
    public TestElement createTestElement() {
        TestSampler testSampler = new TestSampler();
        configureTestElement(testSampler);

        return testSampler;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        super.configureTestElement(element);
        if(element instanceof TestSampler) {
            TestSampler testSampler = (TestSampler) element;
            testSampler.setLabel(label.getText());
            testSampler.setResponseCode(responseCode.getText());
            testSampler.setSuccessful(success.isSelected());

            try{
                int responseTime = Integer.parseInt(this.responseTime.getText());
                testSampler.setResponseTime(responseTime);
            } catch(NumberFormatException e) {
                System.err.println("Response time must be Integer");
            }
        }
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);

        if(element instanceof TestSampler) {
            TestSampler testSampler = (TestSampler)element;
            label.setText(testSampler.getLabel());
            responseCode.setText(testSampler.getResponseCode());
            responseTime.setText(testSampler.getResponseTime().toString());
            success.setSelected(testSampler.getSuccessful());
        }
    }

    @Override
    public void clearGui() {
        super.clearGui();

        label.setText("");
        responseTime.setText("");
        responseCode.setText("");
        success.setSelected(true);
    }
}
