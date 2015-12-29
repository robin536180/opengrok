/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * See LICENSE.txt included in this distribution for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at LICENSE.txt.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

 /*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 */
package org.opensolaris.opengrok.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Placeholder for the information about subgroups of projects and repositories
 *
 * @author Krystof Tulinger
 * @version $Revision$
 */
public class Group implements Comparable<Group> {

    private String name;
    private String pattern = "";
    private Group parent;
    private int flag;

    private List<Group> subgroups = new ArrayList<>();
    private Set<Project> projects = new TreeSet<>();
    private Set<Project> repositories = new TreeSet<>();

    public Set<Project> getProjects() {
        return projects;
    }

    public void addProject(Project p) {
        this.projects.add(p);
    }

    public void addRepository(Project p) {
        this.repositories.add(p);
    }

    public Set<Project> getRepositories() {
        return repositories;
    }

    public void setSubgroups(List<Group> subgroups) {
        this.subgroups = subgroups;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void setRepositories(Set<Project> repositories) {
        this.repositories = repositories;
    }

    public List<Group> getSubgroups() {
        return subgroups;
    }

    public void addGroup(Group g) {
        g.setParent(this);
        subgroups.add(g);
    }

    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * Test group for a match
     *
     * @param p project
     * @return true if project's description matches the group pattern
     */
    public boolean match(Project p) {
        return Pattern.matches("(" + this.pattern + ")", p.getDescription());
    }

    @Override
    public int compareTo(Group o) {
        return getName().toUpperCase(Locale.getDefault()).compareTo(o.getName().toUpperCase(Locale.getDefault()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.name == null ? 0 : this.name.toUpperCase(Locale.getDefault()).hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        return !(this.name != other.name
                && (this.name == null
                || !this.name.toUpperCase(Locale.getDefault()).equals(other.name.toUpperCase(Locale.getDefault()))));
    }

}
