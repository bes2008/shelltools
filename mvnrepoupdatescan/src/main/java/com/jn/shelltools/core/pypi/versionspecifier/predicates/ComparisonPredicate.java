package com.jn.shelltools.core.pypi.versionspecifier.predicates;

import com.jn.langx.util.Maths;
import com.jn.langx.util.Numbers;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.MapAccessor;
import com.jn.shelltools.core.pypi.versionspecifier.VersionPredicate;
import com.jn.shelltools.core.pypi.versionspecifier.VersionSpecifiers;

/**
 *
 * <pre>
 *     >=, >, <, <=
 * </pre>
 *
 * @see com.jn.shelltools.core.pypi.versionspecifier.VersionSpecifiers#VERSION_PATTERN
 *
 * <pre>
 *      dev < pre < post < release
 *  </pre>
 */
public class ComparisonPredicate extends VersionPredicate{

    /**
     * 判定时是否判定 等于。
     */
    private boolean inclusive;
    /**
     * 是否判定 小于。 如果是 true 则判定是否小于 expected, 如果是false，则判定是否 大于 expected
     */
    private boolean lessThan;


    private MapAccessor expectResult;

    public ComparisonPredicate(String expected, boolean inclusive, boolean lessThan) {
        super(expected);
        this.inclusive = inclusive;
        this.lessThan = lessThan;
        this.expectResult = VersionSpecifiers.extractVersionSegments(expected);
    }

    @Override
    public boolean test(String actual) {
        MapAccessor actualResult = VersionSpecifiers.extractVersionSegments(actual);

        int result = compare(expectResult, actualResult);
        if (!lessThan) {
            if (inclusive) {
                return result >= 0;
            } else {
                return result > 0;
            }
        } else {
            if (inclusive) {
                return result <= 0;
            } else {
                return result < 0;
            }
        }
    }

    private int compare(MapAccessor expected, MapAccessor actual) {
        int epochDelta = compareEpochSegment(expected, actual);
        if (epochDelta != 0) {
            return epochDelta;
        }
        int releaseDelta = compareReleaseSegment(expected, actual);
        if (releaseDelta != 0) {
            return releaseDelta;
        }
        int previewDelta = comparePreviewSegment(expected, actual);
        if (previewDelta != 0) {
            return previewDelta;
        }
        int postDelta = comparePostSegment(expected, actual);
        if (postDelta != 0) {
            return postDelta;
        }
        int devDelta = compareDevSegment(expected, actual);
        if (devDelta != 0) {
            return devDelta;
        }
        return 0;
    }

    private int compareEpochSegment(MapAccessor expected, MapAccessor actual) {
        if (!expected.isNull("epoch") && !actual.isNull("epoch")) {
            int epoch1 = expected.getInteger("epoch",0);
            int epoch2 = expected.getInteger("epoch",0);

            return epoch2 - epoch1;
        } else {
            return 0;
        }
    }

    private int compareReleaseSegment(MapAccessor expected, MapAccessor actual) {
        String[] segs1 = Strings.split(expected.getString("release"), ".");
        String[] segs2 = Strings.split(actual.getString("release"), ".");

        int max = Maths.max(segs1.length, segs2.length);
        // 补 0
        if (segs1.length < max) {
            String[] temp = new String[max];
            System.arraycopy(segs1, 0, temp, 0, segs1.length);
            for (int i = segs1.length; i < max; i++) {
                temp[i] = "" + 0;
            }
            segs1 = temp;
        } else if (segs2.length < max) {
            String[] temp = new String[max];
            System.arraycopy(segs2, 0, temp, 0, segs2.length);
            for (int i = segs2.length; i < max; i++) {
                temp[i] = "" + 0;
            }
            segs2 = temp;
        }

        for (int i = 0; i < max; i++) {
            int v1 = Numbers.createInteger(segs1[i]);
            int v2 = Numbers.createInteger(segs2[i]);

            int delta = v2 - v1;
            if (delta != 0) {
                return delta;
            }
        }
        return 0;
    }

    private int comparePreviewSegment(MapAccessor expected, MapAccessor actual) {
        boolean isPre1 = !expected.isNull("pre");
        boolean isPre2 = !actual.isNull("pre");
        if (!isPre1) {
            if (isPre2) {
                return -1;
            } else {
                return 0;
            }
        }
        if (!isPre2) {
            return 1;
        }
        String preLabel1 = expected.getString("preLabel");
        String preLabel2 = actual.getString("preLabel");
        int labelDelta = VersionSpecifiers.comparePreLabel(preLabel1, preLabel2);
        if (labelDelta != 0) {
            return labelDelta;
        }
        int preNumber1 = expected.getInteger("preN", 0);
        int preNumber2 = actual.getInteger("preN", 0);
        return preNumber2 - preNumber1;
    }

    private int comparePostSegment(MapAccessor expected, MapAccessor actual) {
        boolean isPost1 = !expected.isNull("post");
        boolean isPost2 = !actual.isNull("post");
        if (!isPost1) {
            if (isPost2) {
                return -1;
            } else {
                return 0;
            }
        }
        if (!isPost2) {
            return 1;
        }
        int postNumber1 = expected.getInteger("postN", 0);
        int postNumber2 = actual.getInteger("postN", 0);
        return postNumber2 - postNumber1;
    }

    private int compareDevSegment(MapAccessor expected, MapAccessor actual) {
        boolean isDev1 = !expected.isNull("dev");
        boolean isDev2 = !actual.isNull("dev");
        if (!isDev1) {
            if (isDev2) {
                return -1;
            } else {
                return 0;
            }
        }
        if (!isDev2) {
            return 1;
        }
        int devNumber1 = expected.getInteger("devN", 0);
        int devNumber2 = actual.getInteger("devN", 0);
        return devNumber2 - devNumber1;
    }

}
