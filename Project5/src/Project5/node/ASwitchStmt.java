/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import java.util.*;
import Project5.analysis.*;

@SuppressWarnings("nls")
public final class ASwitchStmt extends PStmt
{
    private TSwitch _switch_;
    private TLparen _lparen_;
    private PExpr _expr_;
    private TRparen _rparen_;
    private TLcurly _lcurly_;
    private TCase _case_;
    private TLparen _lctwo_;
    private TIntnum _intnum_;
    private TRparen _rcone_;
    private TColon _colon_;
    private PStmtseq _stmtseq_;
    private PBreakpart _breakpart_;
    private final LinkedList<PMorecases> _morecases_ = new LinkedList<PMorecases>();
    private TDefault _default_;
    private TColon _coltwo_;
    private PStmtseq _stwo_;
    private TRcurly _rctwo_;

    public ASwitchStmt()
    {
        // Constructor
    }

    public ASwitchStmt(
        @SuppressWarnings("hiding") TSwitch _switch_,
        @SuppressWarnings("hiding") TLparen _lparen_,
        @SuppressWarnings("hiding") PExpr _expr_,
        @SuppressWarnings("hiding") TRparen _rparen_,
        @SuppressWarnings("hiding") TLcurly _lcurly_,
        @SuppressWarnings("hiding") TCase _case_,
        @SuppressWarnings("hiding") TLparen _lctwo_,
        @SuppressWarnings("hiding") TIntnum _intnum_,
        @SuppressWarnings("hiding") TRparen _rcone_,
        @SuppressWarnings("hiding") TColon _colon_,
        @SuppressWarnings("hiding") PStmtseq _stmtseq_,
        @SuppressWarnings("hiding") PBreakpart _breakpart_,
        @SuppressWarnings("hiding") List<?> _morecases_,
        @SuppressWarnings("hiding") TDefault _default_,
        @SuppressWarnings("hiding") TColon _coltwo_,
        @SuppressWarnings("hiding") PStmtseq _stwo_,
        @SuppressWarnings("hiding") TRcurly _rctwo_)
    {
        // Constructor
        setSwitch(_switch_);

        setLparen(_lparen_);

        setExpr(_expr_);

        setRparen(_rparen_);

        setLcurly(_lcurly_);

        setCase(_case_);

        setLctwo(_lctwo_);

        setIntnum(_intnum_);

        setRcone(_rcone_);

        setColon(_colon_);

        setStmtseq(_stmtseq_);

        setBreakpart(_breakpart_);

        setMorecases(_morecases_);

        setDefault(_default_);

        setColtwo(_coltwo_);

        setStwo(_stwo_);

        setRctwo(_rctwo_);

    }

    @Override
    public Object clone()
    {
        return new ASwitchStmt(
            cloneNode(this._switch_),
            cloneNode(this._lparen_),
            cloneNode(this._expr_),
            cloneNode(this._rparen_),
            cloneNode(this._lcurly_),
            cloneNode(this._case_),
            cloneNode(this._lctwo_),
            cloneNode(this._intnum_),
            cloneNode(this._rcone_),
            cloneNode(this._colon_),
            cloneNode(this._stmtseq_),
            cloneNode(this._breakpart_),
            cloneList(this._morecases_),
            cloneNode(this._default_),
            cloneNode(this._coltwo_),
            cloneNode(this._stwo_),
            cloneNode(this._rctwo_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASwitchStmt(this);
    }

    public TSwitch getSwitch()
    {
        return this._switch_;
    }

    public void setSwitch(TSwitch node)
    {
        if(this._switch_ != null)
        {
            this._switch_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._switch_ = node;
    }

    public TLparen getLparen()
    {
        return this._lparen_;
    }

    public void setLparen(TLparen node)
    {
        if(this._lparen_ != null)
        {
            this._lparen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lparen_ = node;
    }

    public PExpr getExpr()
    {
        return this._expr_;
    }

    public void setExpr(PExpr node)
    {
        if(this._expr_ != null)
        {
            this._expr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expr_ = node;
    }

    public TRparen getRparen()
    {
        return this._rparen_;
    }

    public void setRparen(TRparen node)
    {
        if(this._rparen_ != null)
        {
            this._rparen_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rparen_ = node;
    }

    public TLcurly getLcurly()
    {
        return this._lcurly_;
    }

    public void setLcurly(TLcurly node)
    {
        if(this._lcurly_ != null)
        {
            this._lcurly_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lcurly_ = node;
    }

    public TCase getCase()
    {
        return this._case_;
    }

    public void setCase(TCase node)
    {
        if(this._case_ != null)
        {
            this._case_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._case_ = node;
    }

    public TLparen getLctwo()
    {
        return this._lctwo_;
    }

    public void setLctwo(TLparen node)
    {
        if(this._lctwo_ != null)
        {
            this._lctwo_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lctwo_ = node;
    }

    public TIntnum getIntnum()
    {
        return this._intnum_;
    }

    public void setIntnum(TIntnum node)
    {
        if(this._intnum_ != null)
        {
            this._intnum_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._intnum_ = node;
    }

    public TRparen getRcone()
    {
        return this._rcone_;
    }

    public void setRcone(TRparen node)
    {
        if(this._rcone_ != null)
        {
            this._rcone_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rcone_ = node;
    }

    public TColon getColon()
    {
        return this._colon_;
    }

    public void setColon(TColon node)
    {
        if(this._colon_ != null)
        {
            this._colon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._colon_ = node;
    }

    public PStmtseq getStmtseq()
    {
        return this._stmtseq_;
    }

    public void setStmtseq(PStmtseq node)
    {
        if(this._stmtseq_ != null)
        {
            this._stmtseq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stmtseq_ = node;
    }

    public PBreakpart getBreakpart()
    {
        return this._breakpart_;
    }

    public void setBreakpart(PBreakpart node)
    {
        if(this._breakpart_ != null)
        {
            this._breakpart_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._breakpart_ = node;
    }

    public LinkedList<PMorecases> getMorecases()
    {
        return this._morecases_;
    }

    public void setMorecases(List<?> list)
    {
        for(PMorecases e : this._morecases_)
        {
            e.parent(null);
        }
        this._morecases_.clear();

        for(Object obj_e : list)
        {
            PMorecases e = (PMorecases) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._morecases_.add(e);
        }
    }

    public TDefault getDefault()
    {
        return this._default_;
    }

    public void setDefault(TDefault node)
    {
        if(this._default_ != null)
        {
            this._default_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._default_ = node;
    }

    public TColon getColtwo()
    {
        return this._coltwo_;
    }

    public void setColtwo(TColon node)
    {
        if(this._coltwo_ != null)
        {
            this._coltwo_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._coltwo_ = node;
    }

    public PStmtseq getStwo()
    {
        return this._stwo_;
    }

    public void setStwo(PStmtseq node)
    {
        if(this._stwo_ != null)
        {
            this._stwo_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stwo_ = node;
    }

    public TRcurly getRctwo()
    {
        return this._rctwo_;
    }

    public void setRctwo(TRcurly node)
    {
        if(this._rctwo_ != null)
        {
            this._rctwo_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rctwo_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._switch_)
            + toString(this._lparen_)
            + toString(this._expr_)
            + toString(this._rparen_)
            + toString(this._lcurly_)
            + toString(this._case_)
            + toString(this._lctwo_)
            + toString(this._intnum_)
            + toString(this._rcone_)
            + toString(this._colon_)
            + toString(this._stmtseq_)
            + toString(this._breakpart_)
            + toString(this._morecases_)
            + toString(this._default_)
            + toString(this._coltwo_)
            + toString(this._stwo_)
            + toString(this._rctwo_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._switch_ == child)
        {
            this._switch_ = null;
            return;
        }

        if(this._lparen_ == child)
        {
            this._lparen_ = null;
            return;
        }

        if(this._expr_ == child)
        {
            this._expr_ = null;
            return;
        }

        if(this._rparen_ == child)
        {
            this._rparen_ = null;
            return;
        }

        if(this._lcurly_ == child)
        {
            this._lcurly_ = null;
            return;
        }

        if(this._case_ == child)
        {
            this._case_ = null;
            return;
        }

        if(this._lctwo_ == child)
        {
            this._lctwo_ = null;
            return;
        }

        if(this._intnum_ == child)
        {
            this._intnum_ = null;
            return;
        }

        if(this._rcone_ == child)
        {
            this._rcone_ = null;
            return;
        }

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._stmtseq_ == child)
        {
            this._stmtseq_ = null;
            return;
        }

        if(this._breakpart_ == child)
        {
            this._breakpart_ = null;
            return;
        }

        if(this._morecases_.remove(child))
        {
            return;
        }

        if(this._default_ == child)
        {
            this._default_ = null;
            return;
        }

        if(this._coltwo_ == child)
        {
            this._coltwo_ = null;
            return;
        }

        if(this._stwo_ == child)
        {
            this._stwo_ = null;
            return;
        }

        if(this._rctwo_ == child)
        {
            this._rctwo_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._switch_ == oldChild)
        {
            setSwitch((TSwitch) newChild);
            return;
        }

        if(this._lparen_ == oldChild)
        {
            setLparen((TLparen) newChild);
            return;
        }

        if(this._expr_ == oldChild)
        {
            setExpr((PExpr) newChild);
            return;
        }

        if(this._rparen_ == oldChild)
        {
            setRparen((TRparen) newChild);
            return;
        }

        if(this._lcurly_ == oldChild)
        {
            setLcurly((TLcurly) newChild);
            return;
        }

        if(this._case_ == oldChild)
        {
            setCase((TCase) newChild);
            return;
        }

        if(this._lctwo_ == oldChild)
        {
            setLctwo((TLparen) newChild);
            return;
        }

        if(this._intnum_ == oldChild)
        {
            setIntnum((TIntnum) newChild);
            return;
        }

        if(this._rcone_ == oldChild)
        {
            setRcone((TRparen) newChild);
            return;
        }

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._stmtseq_ == oldChild)
        {
            setStmtseq((PStmtseq) newChild);
            return;
        }

        if(this._breakpart_ == oldChild)
        {
            setBreakpart((PBreakpart) newChild);
            return;
        }

        for(ListIterator<PMorecases> i = this._morecases_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PMorecases) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._default_ == oldChild)
        {
            setDefault((TDefault) newChild);
            return;
        }

        if(this._coltwo_ == oldChild)
        {
            setColtwo((TColon) newChild);
            return;
        }

        if(this._stwo_ == oldChild)
        {
            setStwo((PStmtseq) newChild);
            return;
        }

        if(this._rctwo_ == oldChild)
        {
            setRctwo((TRcurly) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
