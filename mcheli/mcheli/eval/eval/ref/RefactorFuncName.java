//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.ref;

public class RefactorFuncName extends RefactorAdapter
{
    protected Class targetClass;
    protected String oldName;
    protected String newName;
    
    public RefactorFuncName(final Class targetClass, final String oldName, final String newName) {
        this.targetClass = targetClass;
        this.oldName = oldName;
        this.newName = newName;
        if (oldName == null || newName == null) {
            throw new NullPointerException();
        }
    }
    
    public String getNewFuncName(final Object target, final String name) {
        if (!name.equals(this.oldName)) {
            return null;
        }
        if (this.targetClass == null) {
            if (target == null) {
                return this.newName;
            }
        }
        else if (target != null && this.targetClass.isAssignableFrom(target.getClass())) {
            return this.newName;
        }
        return null;
    }
}
