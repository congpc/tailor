package com.sleekbyte.tailor.functional;

import static org.junit.Assert.assertArrayEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sleekbyte.tailor.Tailor;
import com.sleekbyte.tailor.common.Messages;
import com.sleekbyte.tailor.common.Rules;
import com.sleekbyte.tailor.common.Severity;
import com.sleekbyte.tailor.format.Format;
import com.sleekbyte.tailor.output.Printer;
import com.sleekbyte.tailor.output.ViolationMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tests for {@link Tailor} output formats.
 */
@RunWith(MockitoJUnitRunner.class)
public final class FormatTest {

    protected static final String TEST_INPUT_DIR = "src/test/swift/com/sleekbyte/tailor/functional";
    protected static final String NEWLINE_REGEX = "\\r?\\n";
    protected static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    protected ByteArrayOutputStream outContent;
    protected File inputFile;
    protected List<String> expectedMessages;

    @Before
    public void setUp() throws IOException {
        inputFile = new File(TEST_INPUT_DIR + "/UpperCamelCaseTest.swift");
        expectedMessages = new ArrayList<>();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent, false, Charset.defaultCharset().name()));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testXcodeFormat() throws IOException {
        Format format = Format.XCODE;

        final String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };
        expectedMessages.addAll(getExpectedMsgs().stream().map(msg -> Printer.genOutputStringForTest(
            msg.getRule(),
            inputFile.getName(),
            msg.getLineNumber(),
            msg.getColumnNumber(),
            msg.getSeverity(),
            msg.getMessage())).collect(Collectors.toList()));

        Tailor.main(command);

        List<String> actualOutput = new ArrayList<>();

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);

        // Skip first two lines for file header, last two lines for summary
        msgs = Arrays.copyOfRange(msgs, 4, msgs.length - 2);

        for (String msg : msgs) {
            String truncatedMsg = msg.substring(msg.indexOf(inputFile.getName()));
            actualOutput.add(truncatedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), this.expectedMessages.toArray(),
            actualOutput.toArray());
    }

    @Test
    public void testJSONFormat() throws IOException {
        Format format = Format.JSON;

        final String[] command = new String[] {
            "--format", format.getName(),
            "--no-color",
            "--only=upper-camel-case",
            inputFile.getPath()
        };

        Map<String, Object> expectedOutput = getJSONMessages();

        Tailor.main(command);

        String[] msgs = outContent.toString(Charset.defaultCharset().name()).split(NEWLINE_REGEX);
        msgs = Arrays.copyOfRange(msgs, 2, msgs.length);

        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();
        expectedMessages.addAll(
            Arrays.asList((GSON.toJson(expectedOutput) + System.lineSeparator()).split(NEWLINE_REGEX)));
        for (String msg : expectedMessages) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            expected.add(strippedMsg);
        }
        for (String msg : msgs) {
            String strippedMsg = msg.replaceAll(inputFile.getCanonicalPath(), "");
            actual.add(strippedMsg);
        }

        assertArrayEquals(outContent.toString(Charset.defaultCharset().name()), expected.toArray(), actual.toArray());
    }

    protected List<ViolationMessage> getExpectedMsgs() {
        List<ViolationMessage> messages = new ArrayList<>();
        messages.add(createViolationMessage(3, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES));
        messages.add(createViolationMessage(7, 7, Severity.WARNING, Messages.CLASS + Messages.NAMES));
        messages.add(createViolationMessage(24, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(25, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(26, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(42, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES));
        messages.add(createViolationMessage(43, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(46, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES));
        messages.add(createViolationMessage(47, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(50, 6, Severity.WARNING, Messages.ENUM + Messages.NAMES));
        messages.add(createViolationMessage(55, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(63, 8, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(72, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES));
        messages.add(createViolationMessage(76, 8, Severity.WARNING, Messages.STRUCT + Messages.NAMES));
        messages.add(createViolationMessage(90, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES));
        messages.add(createViolationMessage(94, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES));
        messages.add(createViolationMessage(98, 10, Severity.WARNING, Messages.PROTOCOL + Messages.NAMES));
        messages.add(createViolationMessage(107, 10, Severity.WARNING, Messages.ENUM_CASE + Messages.NAMES));
        messages.add(createViolationMessage(119, 18, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        messages.add(createViolationMessage(119, 23, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        messages.add(createViolationMessage(128, 20, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        messages.add(createViolationMessage(137, 14, Severity.WARNING, Messages.GENERIC_PARAMETERS + Messages.NAMES));
        return messages;
    }

    private ViolationMessage createViolationMessage(int line, int column, Severity severity, String msg) {
        return new ViolationMessage(Rules.UPPER_CAMEL_CASE, line, column, severity, msg + Messages.UPPER_CAMEL_CASE);
    }

    private Map<String, Object> getJSONSummary(long analyzed, long skipped, long errors, long warnings) {
        Map<String, Object> summary = new HashMap<>();
        summary.put(Messages.ANALYZED_KEY, analyzed);
        summary.put(Messages.SKIPPED_KEY, skipped);
        summary.put(Messages.VIOLATIONS_KEY, errors + warnings);
        summary.put(Messages.ERRORS_KEY, errors);
        summary.put(Messages.WARNINGS_KEY, warnings);
        return summary;
    }

    private Map<String, Object> getJSONMessages() {
        List<Map<String, Object>> violations = new ArrayList<>();
        for (ViolationMessage msg : getExpectedMsgs()) {
            Map<String, Object> violation = new HashMap<>();
            Map<String, Object> location = new HashMap<>();
            location.put(Messages.LINE_KEY, msg.getLineNumber());
            location.put(Messages.COLUMN_KEY, msg.getColumnNumber());
            violation.put(Messages.LOCATION_KEY, location);
            violation.put(Messages.SEVERITY_KEY, msg.getSeverity().toString());
            violation.put(Messages.RULE_KEY, Rules.UPPER_CAMEL_CASE.getName());
            violation.put(Messages.MESSAGE_KEY, msg.getMessage());
            violations.add(violation);
        }

        Map<String, Object> file = new HashMap<>();
        file.put(Messages.PATH_KEY, "");
        file.put(Messages.PARSED_KEY, true);
        file.put(Messages.VIOLATIONS_KEY, violations);

        List<Object> files = new ArrayList<>();
        files.add(file);

        Map<String, Object> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put(Messages.FILES_KEY, files);
        expectedOutput.put(Messages.SUMMARY_KEY, getJSONSummary(1, 0, 0, 22));
        return expectedOutput;
    }

}
