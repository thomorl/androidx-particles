/*
 * Copyright (c) 2013-2018 Raul Portales  (@plattysoft) and contributors,
 *               2020      Thomas Orlando (@thomorl) and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.particles.modifiers;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.particles.Particle;

public class ScaleModifier implements ParticleModifier {

	private float mStartValue;
	private float mEndValue;
	private long mStartTime;
	private long mEndTime;
	private long mDuration;
	private float mValueIncrement;
	private Interpolator mInterpolator;

	public ScaleModifier(float startValue, float endValue, long startMillis, long endMillis,
						 @NonNull Interpolator interpolator) {
		mStartValue = startValue;
		mEndValue = endValue;
		mStartTime = startMillis;
		mEndTime = endMillis;
		mDuration = mEndTime - mStartTime;
		mValueIncrement = mEndValue - mStartValue;
		mInterpolator = interpolator;
	}
	
	public ScaleModifier(float startValue, float endValue, long startMillis, long endMillis) {
		this(startValue, endValue, startMillis, endMillis, new LinearInterpolator());
	}
	
	@Override
	public void apply(@NonNull Particle particle, long milliseconds) {
		if (milliseconds < mStartTime) {
			particle.mScale = mStartValue;
		}
		else if (milliseconds > mEndTime) {
			particle.mScale = mEndValue;
		}
		else {
			float interpolatedValue = mInterpolator.getInterpolation((milliseconds - mStartTime)*1f/mDuration);
			float newScale = mStartValue + mValueIncrement*interpolatedValue;
			particle.mScale = newScale;
		}
	}

}
