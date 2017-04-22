/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import Project5.analysis.*;

@SuppressWarnings("nls")
public final class ADivideMultop extends PMultop
{
    private TDivide _divide_;

    public ADivideMultop()
    {
        // Constructor
    }

    public ADivideMultop(
        @SuppressWarnings("hiding") TDivide _divide_)
    {
        // Constructor
        setDivide(_divide_);

    }

    @Override
    public Object clone()
    {
        return new ADivideMultop(
            cloneNode(this._divide_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADivideMultop(this);
    }

    public TDivide getDivide()
    {
        return this._divide_;
    }

    public void setDivide(TDivide node)
    {
        if(this._divide_ != null)
        {
            this._divide_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._divide_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._divide_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._divide_ == child)
        {
            this._divide_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._divide_ == oldChild)
        {
            setDivide((TDivide) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
