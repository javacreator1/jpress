/**
 * Copyright (c) 2016-2019, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.core.addon;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.Controller;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AddonInfo implements Serializable {

    public static final int STATUS_INIT = 0;
    public static final int STATUS_INSTALL = 1;
    public static final int STATUS_START = 2;

    private static final Log log = Log.getLog(AddonInfo.class);

    private String id;
    private String title;
    private String description;
    private String author;
    private String authorWebsite;
    private String version;
    private int versionCode;
    private String updateUrl;

    private Class<? extends Addon> addonClass;
    private int status = STATUS_INIT;

    private List<Class<? extends Controller>> controllers;
    private List<Class<? extends Interceptor>> interceptors;
    private List<Class<? extends Handler>> handlers;

    public AddonInfo() {

    }

    public AddonInfo(Properties properties) {
        this.id = properties.getProperty("id");
        this.title = properties.getProperty("title");
        this.description = properties.getProperty("description");
        this.author = properties.getProperty("author");
        this.authorWebsite = properties.getProperty("authorWebsite");
        this.version = properties.getProperty("version");
        this.versionCode = Integer.valueOf(properties.getProperty("versionCode", "1"));
        this.updateUrl = properties.getProperty("updateUrl");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<? extends Addon> getAddonClass() {
        return addonClass;
    }

    public void setAddonClass(Class<? extends Addon> addonClass) {
        this.addonClass = addonClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorWebsite() {
        return authorWebsite;
    }

    public void setAuthorWebsite(String authorWebsite) {
        this.authorWebsite = authorWebsite;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isInstall() {
        return status > STATUS_INIT;
    }

    public boolean isStart() {
        return status > STATUS_INSTALL;
    }

    public void addController(Class<? extends Controller> clazz) {
        if (controllers == null) {
            controllers = new ArrayList<>();
        }
        controllers.add(clazz);
    }

    public List<Class<? extends Controller>> getControllers() {
        return controllers;
    }

    public void setControllers(List<Class<? extends Controller>> controllers) {
        this.controllers = controllers;
    }


    public void addInterceptor(Class<? extends Interceptor> clazz) {
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(clazz);
    }

    public List<Class<? extends Interceptor>> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<Class<? extends Interceptor>> interceptors) {
        this.interceptors = interceptors;
    }

    public void addHandler(Class<? extends Handler> clazz) {
        if (handlers == null) {
            handlers = new ArrayList<>();
        }
        handlers.add(clazz);
    }

    public List<Class<? extends Handler>> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Class<? extends Handler>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof AddonInfo)) {
            return false;
        }

        AddonInfo addon = (AddonInfo) obj;
        if (addon.getId() == null) {
            return false;
        }

        return addon.getId().equals(getId());
    }

    public File buildJarFile() {

        String webRoot = PathKit.getWebRootPath();

        StringBuilder fileName = new StringBuilder(webRoot);
        fileName.append(File.separator);
        fileName.append("WEB-INF");
        fileName.append(File.separator);
        fileName.append("addons");
        fileName.append(File.separator);
        fileName.append(getId());
        fileName.append(".jar");

        return new File(fileName.toString());
    }

}
