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
package androidx.particles;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.NonNull;

/**
 * A single, animated 2D particle. Used if a {@link ParticleSystem} is initialized with an
 * {@link AnimationDrawable}.
 *
 * @see ParticleSystem#ParticleSystem(Activity, int, AnimationDrawable, long) 
 */
public class AnimatedParticle extends Particle {

	@NonNull
	private AnimationDrawable mAnimationDrawable;
	private int mTotalTime;

	public AnimatedParticle(@NonNull AnimationDrawable animationDrawable) {
		mAnimationDrawable = animationDrawable;
		mImage = ((BitmapDrawable) mAnimationDrawable.getFrame(0)).getBitmap();
		// If it is a repeating animation, calculate the time
		mTotalTime = 0;
		for (int i=0; i<mAnimationDrawable.getNumberOfFrames(); i++) {
			mTotalTime += mAnimationDrawable.getDuration(i);
		}
	}

	@Override
	public boolean update(long milliseconds) {
		boolean active = super.update(milliseconds);
		if (active) {
			long animationElapsedTime = 0;
			long realMilliseconds = milliseconds - mStartingMillisecond;
			if (realMilliseconds > mTotalTime) {
				if (mAnimationDrawable.isOneShot()) {
					return false;
				}
				else {
					realMilliseconds = realMilliseconds % mTotalTime;
				}
			}
			for (int i=0; i<mAnimationDrawable.getNumberOfFrames(); i++) {
				animationElapsedTime += mAnimationDrawable.getDuration(i);
				if (animationElapsedTime > realMilliseconds) {
					mImage = ((BitmapDrawable) mAnimationDrawable.getFrame(i)).getBitmap();
					break;
				}
			}
		}
		return active;
	}
}
