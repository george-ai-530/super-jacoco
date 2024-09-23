package com.xiaoju.basetech.util;



import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;


/**
 * {@code @description:} git 操作工具类
 * {@code @author:} gaoweiwei_v
 * {@code @time:} 2019/6/20 4:28 PM
 */
@Component
public class GitHandler {
    static final Logger logger = LoggerFactory.getLogger(GitHandler.class);

    @Value(value = "${gitlab.username}")
    private  String username;

    @Value(value = "${gitlab.password}")
    private  String password;

    public void cloneRepository(String gitUrl, String codePath, String commitId) throws GitAPIException, IOException {

        Git git = Git.cloneRepository()
                .setURI(gitUrl)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                .setDirectory(new File(codePath))
                .setBranch(commitId)
                .call();
        // 切换到指定commitId
        checkoutBranch(git, commitId);
    }

    public void pull(String codePath){
        try {
            Git git = Git.open(new File(codePath));

            PullCommand pullCommand = git.pull();
            CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
            pullCommand.setCredentialsProvider(credentialsProvider);
            pullCommand.call();
            logger.info("pull success "+codePath);
        } catch (Exception e) {
            logger.error("pull error", e);
        }
    }

    private static void checkoutBranch(Git git, String branch) {
        try {
            git.checkout()
                    .setName(branch)
                    .call();
        } catch (GitAPIException e) {
            throw new IllegalStateException(e);
        }
    }

    public static boolean isValidGitRepository(String codePath) {
        Path folder = Paths.get(codePath);
        if (Files.exists(folder) && Files.isDirectory(folder)) {
            // If it has been at least initialized
            // we are assuming that the clone worked at that time, caller should call hasAtLeastOneReference
            return RepositoryCache.FileKey.isGitRepository(folder.toFile(), FS.DETECTED);
        } else {
            return false;
        }
    }


}