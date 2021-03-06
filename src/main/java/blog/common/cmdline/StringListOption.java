/*
 * Copyright (c) 2005, Regents of the University of California
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.  
 *
 * * Neither the name of the University of California, Berkeley nor
 *   the names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior 
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package blog.common.cmdline;

import java.util.ArrayList;
import java.util.List;

/**
 * Option that can occur multiple times on the command line with a string
 * argument, yielding a list of strings. The order of strings in the list is the
 * order in which they were given on the command line. The default value of a
 * string list option is an empty list.
 */
public class StringListOption extends AbstractOption {
  /**
   * Creates a string list option and registers it with the Parser class.
   * 
   * @param shortForm
   *          single-character form of this option, or null for an option with
   *          no short form
   * 
   * @param longForm
   *          long form of this option, or null for an option with no long form.
   * 
   * @param docStr
   *          short (preferably less than 40 characters) string specifying what
   *          happens when this option is "&lt;s&gt;"
   */
  public StringListOption(String shortForm, String longForm, String docStr) {
    super(shortForm, longForm);
    this.docStr = docStr;

    Parser.addOption(this);
  }

  public boolean expectsValue() {
    return true;
  }

  public void recordOccurrence(String form, String valueStr) {
    values.add(valueStr);
  }

  public String getUsageString() {
    StringBuffer buf = new StringBuffer();
    if (!shortForms.isEmpty()) {
      buf.append("-" + shortForms.get(0) + " <s>");
      if (!longForms.isEmpty()) {
        buf.append(", ");
      }
    }
    if (!longForms.isEmpty()) {
      buf.append("--" + longForms.get(0) + " <s>");
    }

    while (buf.length() < DOC_OFFSET) {
      buf.append(" ");
    }
    buf.append(docStr);

    return buf.toString();
  }

  /**
   * Returns the values specified on the command line for this option, or an
   * empty list if the option did not occur.
   * 
   * @return List of String
   */
  public List<String> getValue() {
    return values;
  }

  private String docStr;

  private List<String> values = new ArrayList<String>(); // of String
}
