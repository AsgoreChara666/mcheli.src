//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "D:\mcp_stable-12-1.7.10"!

//Decompiled by Procyon!

package mcheli.eval.eval.lex;

import java.util.*;
import mcheli.eval.eval.rule.*;
import mcheli.eval.eval.exp.*;

public class LexFactory
{
    public Lex create(final String str, final List[] opeList, final ShareRuleValue share, final ShareExpValue exp) {
        return new Lex(str, opeList, share.paren, exp);
    }
}
