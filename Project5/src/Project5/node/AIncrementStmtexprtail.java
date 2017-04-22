/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import Project5.analysis.*;

@SuppressWarnings("nls")
public final class AIncrementStmtexprtail extends PStmtexprtail
{
    private TId _id_;
    private TPlus _plus_;
    private TPlus _dblplus_;

    public AIncrementStmtexprtail()
    {
        // Constructor
    }

    public AIncrementStmtexprtail(
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") TPlus _plus_,
        @SuppressWarnings("hiding") TPlus _dblplus_)
    {
        // Constructor
        setId(_id_);

        setPlus(_plus_);

        setDblplus(_dblplus_);

    }

    @Override
    public Object clone()
    {
        return new AIncrementStmtexprtail(
            cloneNode(this._id_),
            cloneNode(this._plus_),
            cloneNode(this._dblplus_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIncrementStmtexprtail(this);
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

    public TPlus getPlus()
    {
        return this._plus_;
    }

    public void setPlus(TPlus node)
    {
        if(this._plus_ != null)
        {
            this._plus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._plus_ = node;
    }

    public TPlus getDblplus()
    {
        return this._dblplus_;
    }

    public void setDblplus(TPlus node)
    {
        if(this._dblplus_ != null)
        {
            this._dblplus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dblplus_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._id_)
            + toString(this._plus_)
            + toString(this._dblplus_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._id_ == child)
        {
            this._id_ = null;
            return;
        }

        if(this._plus_ == child)
        {
            this._plus_ = null;
            return;
        }

        if(this._dblplus_ == child)
        {
            this._dblplus_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._id_ == oldChild)
        {
            setId((TId) newChild);
            return;
        }

        if(this._plus_ == oldChild)
        {
            setPlus((TPlus) newChild);
            return;
        }

        if(this._dblplus_ == oldChild)
        {
            setDblplus((TPlus) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
