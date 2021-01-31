package com.ming.sjll.Message.extend;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.plugin.ImagePlugin;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

public class CustomExtensionModule extends DefaultExtensionModule {
    private PdfPlugin pdfPlugin;
    private CooperationPlugin cooperationPlugin;
    private ChangeProjectPlugin changeProjectPlugin;
    private ComplaintPlugin complaintPlugin;
    private ImagePlugin imagePlugin;
    private FilePlugin filePlugin;
    private CustomLocationPlugin locationPlugin;

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = new ArrayList<>();
        pdfPlugin = new PdfPlugin();
        pluginModules.add(pdfPlugin);
        //单聊有合作和切换项目
        if (conversationType != Conversation.ConversationType.GROUP) {
            cooperationPlugin = new CooperationPlugin();
            changeProjectPlugin = new ChangeProjectPlugin();

            pluginModules.add(cooperationPlugin);
            pluginModules.add(changeProjectPlugin);

        }
        complaintPlugin = new ComplaintPlugin();
        pluginModules.add(complaintPlugin);

        imagePlugin = new CustomImagePlugin();
        pluginModules.add(imagePlugin);

        filePlugin = new CustomFilePlugin();
        pluginModules.add(filePlugin);

        locationPlugin = new CustomLocationPlugin();
        pluginModules.add(locationPlugin);


        return pluginModules;
    }
}
