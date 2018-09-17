package com.sailesh.sparks.githubprs.util;

import com.sailesh.sparks.githubprs.model.PullRequestDiffRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class Util {
    public static DateFormat dateFormat = new SimpleDateFormat("MM dd, yyyy");
    private static final int DEFAULT = 0;
    private static final int NEGATIVE = 1;
    private static final int POSITIVE = 2;

    public static List<PullRequestDiffRow> parsePRDiffResponse(ResponseBody responseBody) {
        ArrayList<PullRequestDiffRow> diffFilesList = new ArrayList<>();

        InputStreamReader inputReader = new InputStreamReader(responseBody.byteStream());
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        String line;
        PullRequestDiffRow pullRequestDiffRow = null;

        try {
            int prevLine = DEFAULT;
            ArrayList<String> negativeList = new ArrayList<>();
            ArrayList<String> positiveList = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {

                String[] splitLine = line.split(" ", 2);

                if (splitLine.length >= 1) {

                    String identifier = splitLine[0];
                    String content = splitLine.length == 2 ? splitLine[1] : "";
                    String completeString = identifier + " " + content;

                    if (identifier.equals("diff")) {
                        if (pullRequestDiffRow != null) {
                            diffFilesList.add(pullRequestDiffRow);
                        }

                        pullRequestDiffRow = new PullRequestDiffRow();
                        pullRequestDiffRow.setRowTitle(completeString);
                        pullRequestDiffRow.setRowDataList(new ArrayList<String[]>());

                        prevLine = DEFAULT;

                    } else if (identifier.contains("-") && pullRequestDiffRow != null) {
                        if (prevLine == DEFAULT) {
                            negativeList.add(completeString);

                        } else if (prevLine == NEGATIVE) {
                            negativeList.add(completeString);

                        }
                        prevLine = NEGATIVE;

                    } else if (identifier.contains("+") && pullRequestDiffRow != null) {
                        if (prevLine == DEFAULT) {
                            positiveList.add(completeString);

                        } else if (prevLine == POSITIVE) {
                            positiveList.add(completeString);

                        } else {
                            positiveList.add(completeString);
                        }

                        prevLine = POSITIVE;

                    } else if (pullRequestDiffRow != null) {
                        pullRequestDiffRow.getRowDataList().addAll(setPRDifferences(negativeList, positiveList));
                        pullRequestDiffRow.getRowDataList().add(new String[]{identifier + content});

                        prevLine = DEFAULT;
                    }
                }
            }
            if (pullRequestDiffRow != null) {
                diffFilesList.add(pullRequestDiffRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diffFilesList;
    }


    private static ArrayList<String[]> setPRDifferences(ArrayList<String> negativeList, ArrayList<String> positiveList) {
        ArrayList<String[]> prDiffRows = new ArrayList<>();
        boolean isNegativeRowEmpty = negativeList == null && negativeList.size() <= 0;
        boolean isPositiveRowEmpty = positiveList == null && positiveList.size() <= 0;

        if (isNegativeRowEmpty && isPositiveRowEmpty) {
            return prDiffRows;
        } else if (isNegativeRowEmpty) {

            for (String s : positiveList) {
                prDiffRows.add(new String[]{"", s});
            }
            positiveList.clear();
        } else if (isPositiveRowEmpty) {

            for (String s : negativeList) {
                prDiffRows.add(new String[]{s, ""});
            }
            negativeList.clear();
        } else {

            if (negativeList.size() > positiveList.size()) {
                for (int i = 0; i < negativeList.size(); i++) {

                    if (i < positiveList.size()) {
                        prDiffRows.add(new String[]{
                                negativeList.get(i), positiveList.get(i)
                        });
                    } else {
                        prDiffRows.add(new String[]{"", negativeList.get(i)});
                    }
                }
                negativeList.clear();
                positiveList.clear();
            } else {
                for (int i = 0; i < positiveList.size(); i++) {
                    if (i < negativeList.size()) {
                        prDiffRows.add(new String[]{
                                negativeList.get(i), positiveList.get(i)
                        });
                    } else {
                        prDiffRows.add(new String[]{"", positiveList.get(i)});
                    }
                }
                negativeList.clear();
                positiveList.clear();
            }
        }

        return prDiffRows;
    }
}
