/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import Project5.analysis.*;

@SuppressWarnings("nls")
public final class AForStmt extends PStmt
{
    private TFor _for_;
    private TLparen _lparen_;
    private PType _type_;
    private TId _id_;
    private TAssign _assign_;
    private PExpr _expr_;
    private TSemicolon _semicolon_;
    private PBoolean _boolean_;
    private TSemicolon _stwo_;
    private PStmtexprtail _stmtexprtail_;
    private TRparen _rparen_;
    private TLcurly _lcurly_;
    private PStmtseq _stmtseq_;
    private TRcurly _rcurly_;

    public AForStmt()
    {
        // Constructor
    }

    public AForStmt(
        @SuppressWarnings("hiding") TFor _for_,
        @SuppressWarnings("hiding") TLparen _lparen_,
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") TAssign _assign_,
        @SuppressWarnings("hiding") PExpr _expr_,
        @SuppressWarnings("hiding") TSemicolon _semicolon_,
        @SuppressWarnings("hiding") PBoolean _boolean_,
        @SuppressWarnings("hiding") TSemicolon _stwo_,
        @SuppressWarnings("hiding") PStmtexprtail _stmtexprtail_,
        @SuppressWarnings("hiding") TRparen _rparen_,
        @SuppressWarnings("hiding") TLcurly _lcurly_,
        @SuppressWarnings("hiding") PStmtseq _stmtseq_,
        @SuppressWarnings("hiding") TRcurly _rcurly_)
    {
        // Constructor
        setFor(_for_);

        setLparen(_lparen_);

        setType(_type_);

        setId(_id_);

        setAssign(_assign_);

        setExpr(_expr_);

        setSemicolon(_semicolon_);

        setBoolean(_boolean_);

        setStwo(_stwo_);

        setStmtexprtail(_stmtexprtail_);

        setRparen(_rparen_);

        setLcurly(_lcurly_);

        setStmtseq(_stmtseq_);

        setRcurly(_rcurly_);

    }

    @Override
    public Object clone()
    {
        return new AForStmt(
            cloneNode(this._for_),
            cloneNode(this._lparen_),
            cloneNode(this._type_),
            cloneNode(this._id_),
            cloneNode(this._assign_),
            cloneNode(this._expr_),
            cloneNode(this._semicolon_),
            cloneNode(this._boolean_),
            cloneNode(this._stwo_),
            cloneNode(this._stmtexprtail_),
            cloneNode(this._rparen_),
            cloneNode(this._lcurly_),
            cloneNode(this._stmtseq_),
            cloneNode(this._rcurly_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAForStmt(this);
    }

    public TFor getFor()
    {
        return this._for_;
    }

    public void setFor(TFor node)
    {
        if(this._for_ != null)
        {
            this._for_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._for_ = node;
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

    public PType getType()
    {
        return this._type_;
    }

    public void setType(PType node)
    {
        if(this._type_ != null)
        {
            this._type_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._type_ = node;
    }

    public TId getId()
    {
        return this._id_;
    }

    public void setId(TId node)
    {
        if(this._id_ != null)
        {
            this._id_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._id_ = node;
    }

    public TAssign getAssign()
    {
        return this._assign_;
    }

    public void setAssign(TAssign node)
    {
        if(this._assign_ != null)
        {
            this._assign_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._assign_ = node;
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

    public TSemicolon getSemicolon()
    {
        return this._semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if(this._semicolon_ != null)
        {
            this._semicolon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolon_ = node;
    }

    public PBoolean getBoolean()
    {
        return this._boolean_;
    }

    public void setBoolean(PBoolean node)
    {
        if(this._boolean_ != null)
        {
            this._boolean_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._boolean_ = node;
    }

    public TSemicolon getStwo()
    {
        return this._stwo_;
    }

    public void setStwo(TSemicolon node)
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

    public PStmtexprtail getStmtexprtail()
    {
        return this._stmtexprtail_;
    }

    public void setStmtexprtail(PStmtexprtail node)
    {
        if(this._stmtexprtail_ != null)
        {
            this._stmtexprtail_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stmtexprtail_ = node;
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

    public TRcurly getRcurly()
    {
        return this._rcurly_;
    }

    public void setRcurly(TRcurly node)
    {
        if(this._rcurly_ != null)
        {
            this._rcurly_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rcurly_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._for_)
            + toString(this._lparen_)
            + toString(this._type_)
            + toString(this._id_)
            + toString(this._assign_)
            + toString(this._expr_)
            + toString(this._semicolon_)
            + toString(this._boolean_)
            + toString(this._stwo_)
            + toString(this._stmtexprtail_)
            + toString(this._rparen_)
            + toString(this._lcurly_)
            + toString(this._stmtseq_)
            + toString(this._rcurly_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._for_ == child)
        {
            this._for_ = null;
            return;
        }

        if(this._lparen_ == child)
        {
            this._lparen_ = null;
            return;
        }

        if(this._type_ == child)
        {
            this._type_ = null;
            return;
        }

        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._assign_ == child)
        {
            this._assign_ = null;
            return;
        }

        if(this._expr_ == child)
        {
            this._expr_ = null;
            return;
        }

        if(this._semicolon_ == child)
        {
            this._semicolon_ = null;
            return;
        }

        if(this._boolean_ == child)
        {
            this._boolean_ = null;
            return;
        }

        if(this._stwo_ == child)
        {
            this._stwo_ = null;
            return;
        }

        if(this._stmtexprtail_ == child)
        {
            this._stmtexprtail_ = null;
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

        if(this._stmtseq_ == child)
        {
            this._stmtseq_ = null;
            return;
        }

        if(this._rcurly_ == child)
        {
            this._rcurly_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._for_ == oldChild)
        {
            setFor((TFor) newChild);
            return;
        }

        if(this._lparen_ == oldChild)
        {
            setLparen((TLparen) newChild);
            return;
        }

        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        if(this._assign_ == oldChild)
        {
            setAssign((TAssign) newChild);
            return;
        }

        if(this._expr_ == oldChild)
        {
            setExpr((PExpr) newChild);
            return;
        }

        if(this._semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

        if(this._boolean_ == oldChild)
        {
            setBoolean((PBoolean) newChild);
            return;
        }

        if(this._stwo_ == oldChild)
        {
            setStwo((TSemicolon) newChild);
            return;
        }

        if(this._stmtexprtail_ == oldChild)
        {
            setStmtexprtail((PStmtexprtail) newChild);
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

        if(this._stmtseq_ == oldChild)
        {
            setStmtseq((PStmtseq) newChild);
            return;
        }

        if(this._rcurly_ == oldChild)
        {
            setRcurly((TRcurly) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
