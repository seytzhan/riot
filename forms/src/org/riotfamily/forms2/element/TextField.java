/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.riotfamily.forms2.element;

import org.riotfamily.forms2.base.Element;
import org.riotfamily.forms2.base.ElementState;
import org.riotfamily.forms2.base.UserInterface;
import org.riotfamily.forms2.client.Html;
import org.riotfamily.forms2.value.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConversionServiceFactory;

public class TextField extends Element {

	private transient ConversionService conversionService = ConversionServiceFactory.createDefaultConversionService();
	
	public class State extends ElementState {

		private String text;
		
		@Override
		protected void onInit(Value value) {
			Object obj = value.get();
			if (obj != null) {
				text = conversionService.convert(obj, String.class);
			}
		}
		
		protected String getText() {
			return text;
		}

		@Override
		protected void renderElement(Html html) {
			html.input("text", text).propagate("change", "update");
		}
		
		public void update(UserInterface ui, String text) {
			this.text = text;
		}

		@Override
		public void populate(Value value) {
			Class<?> type = value.require(Object.class, String.class).getTypeDescriptor().getType();
			Object obj = conversionService.convert(text, type);
			value.set(obj);
		}
		
	}
}