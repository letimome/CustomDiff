package customs.peering;

/*
 * Copyright (C) 2011, 2012, IBM Corporation and others.
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 * available at http://www.eclipse.org/org/documents/edl-v10.php
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.ApplyCommand;
import org.eclipse.jgit.api.ApplyResult;
import org.eclipse.jgit.api.GitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.PatchApplyException;
import org.eclipse.jgit.api.errors.PatchFormatException;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;
import org.eclipse.jgit.patch.Patch;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;

/**
 * Apply a patch to files and/or to the index.
 *
 * @see <a href="http://www.kernel.org/pub/software/scm/git/docs/git-apply.html"
 *      >Git documentation about apply</a>
 * @since 2.0
 */
public class ApplyPatchCommand extends GitCommand<ApplyResult> {

	private InputStream in;
	File fileToPatch;
	private String pathToResource = "./src/main/resources/";

	/**
	 * Constructs the command if the patch is to be applied to the index.
	 *
	 * @param repo
	 */
	
	
	ApplyPatchCommand(Repository repo) {
		super(repo);
	}

	/**
	 * Set patch
	 *
	 * @param in
	 *            the patch to apply
	 * @return this instance
	 */
	public ApplyPatchCommand setPatch(InputStream in) {
	//	checkCallable();
		this.in = in;
		try {
			System.out.println("READ");
			System.out.println(in.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Executes the {@code ApplyCommand} command with all the options and
	 * parameters collected by the setter methods (e.g.
	 * {@link #setPatch(InputStream)} of this class. Each instance of this class
	 * should only be used for one invocation of the command. Don't call this
	 * method twice on an instance.
	 */

	public ApplyResult call() throws GitAPIException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ApplyResult call(File fileToPatch, String forwhich3way) throws GitAPIException, PatchFormatException,
			PatchApplyException {
		
		checkCallable();
		System.out.println("Calling to patch call ");
		ApplyResult r = new ApplyResult();
		try {
			final Patch p = new Patch();
			try {
				
				p.parse(in); //needs first to call set patch
				System.out.println("PARSED IN\n:"+in);
				System.out.println("Patch \n:"+p.getFiles());
			} finally {
				in.close();
			}
			if (!p.getErrors().isEmpty())
				throw new PatchFormatException(p.getErrors());
			
			if (p.getFiles().size() ==0) 
				System.out.println("\n\nEMPTY files size to apply command"+p.getFiles().size()+"\n\n");
			
			for (FileHeader fh : p.getFiles()) {
				ChangeType type = fh.getChangeType();
				File f = null;
				System.out.println("TRYING to get FILE path: "+pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getNewPath());
				switch (type) {
				case ADD:
					f = getFile(pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getNewPath(), true);
					apply(f, fh);
					break;
				case MODIFY:
					
					f = getFile(pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getOldPath(), false);
					apply(f, fh);
					break;
				case DELETE:
					f = getFile(pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getOldPath(), false);
					if (!f.delete())
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().cannotDeleteFile, f));
					break;
				case RENAME:
					f = getFile(pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getOldPath(), false);
					File dest = getFile(pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getNewPath(), false);
					try {
						//FileUtils.rename(f, dest, StandardCopyOption.ATOMIC_MOVE);
					} catch (Exception e) {
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().renameFileFailed, f, dest), e);
					}
					break;
				case COPY:
					f = getFile(pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getOldPath(), false);
					byte[] bs = IO.readFully(f);
					FileOutputStream fos = new FileOutputStream(getFile(
							pathToResource+"kdiff-workspace/"+forwhich3way+"/"+fh.getNewPath(),
							true));
					try {
						fos.write(bs);
					} finally {
						fos.close();
					}
				}
				r.addUpdatedFile(f);
			}
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(
					JGitText.get().patchApplyException, e.getMessage()), e);
		}
		setCallable(false);
		return r;
	}

	private File getFile(String path, boolean create)
			throws PatchApplyException {
		System.out.println("PATH TO FETCH: "+path);
		File f = new File(path);
		if (create)
			try {
				File parent = f.getParentFile();
				FileUtils.mkdirs(parent, true);
				FileUtils.createNewFile(f);
			} catch (IOException e) {
				throw new PatchApplyException(MessageFormat.format(
						JGitText.get().createNewFileFailed, f), e);
			}
		return f;
	}

	/**
	 * @param f
	 * @param fh
	 * @throws IOException
	 * @throws PatchApplyException
	 */
	public  void apply(File f, FileHeader fh)
			throws IOException, PatchApplyException {
		RawText rt = new RawText(f);
		List<String> oldLines = new ArrayList<>(rt.size());
		for (int i = 0; i < rt.size(); i++)
			oldLines.add(rt.getString(i));
		List<String> newLines = new ArrayList<>(oldLines);
		for (HunkHeader hh : fh.getHunks()) {

			byte[] b = new byte[hh.getEndOffset() - hh.getStartOffset()];
			System.arraycopy(hh.getBuffer(), hh.getStartOffset(), b, 0,
					b.length);
			RawText hrt = new RawText(b);

			List<String> hunkLines = new ArrayList<>(hrt.size());
			for (int i = 0; i < hrt.size(); i++)
				hunkLines.add(hrt.getString(i));
			int pos = 0;
			for (int j = 1; j < hunkLines.size(); j++) {
				String hunkLine = hunkLines.get(j);
				switch (hunkLine.charAt(0)) {
				case ' ':
					if (!newLines.get(hh.getNewStartLine() - 1 + pos).equals(
							hunkLine.substring(1))) {
						throw new PatchApplyException(MessageFormat.format(
								JGitText.get().patchApplyException, hh));
					}
					pos++;
					break;
				case '-':
					if (hh.getNewStartLine() == 0) {
						newLines.clear();
					} else {
						if (!newLines.get(hh.getNewStartLine() - 1 + pos)
								.equals(hunkLine.substring(1))) {
							throw new PatchApplyException(MessageFormat.format(
									JGitText.get().patchApplyException, hh));
						}
						newLines.remove(hh.getNewStartLine() - 1 + pos);
					}
					break;
				case '+':
					newLines.add(hh.getNewStartLine() - 1 + pos,
							hunkLine.substring(1));
					pos++;
					break;
				}
			}
		}
		if (!isNoNewlineAtEndOfFile(fh))
			newLines.add(""); //$NON-NLS-1$
		if (!rt.isMissingNewlineAtEnd())
			oldLines.add(""); //$NON-NLS-1$
		if (!isChanged(oldLines, newLines))
			return; // don't touch the file
		StringBuilder sb = new StringBuilder();
		for (String l : newLines) {
			// don't bother handling line endings - if it was windows, the \r is
			// still there!
			sb.append(l).append('\n');
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		try (FileWriter fw = new FileWriter(f)) {
			fw.write(sb.toString());
		}

		//leti commented getRepository().getFS().setExecute(f, fh.getNewMode() == FileMode.EXECUTABLE_FILE);
	}

	private static boolean isChanged(List<String> ol, List<String> nl) {
		if (ol.size() != nl.size())
			return true;
		for (int i = 0; i < ol.size(); i++)
			if (!ol.get(i).equals(nl.get(i)))
				return true;
		return false;
	}

	private boolean isNoNewlineAtEndOfFile(FileHeader fh) {
		HunkHeader lastHunk = fh.getHunks().get(fh.getHunks().size() - 1);
		RawText lhrt = new RawText(lastHunk.getBuffer());
		return lhrt.getString(lhrt.size() - 1).equals(
				"\\ No newline at end of file"); //$NON-NLS-1$
	}



	
}