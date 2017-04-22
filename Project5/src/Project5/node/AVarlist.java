/* This file was generated by SableCC (http://www.sablecc.org/). */

package Project5.node;

import java.util.*;
import Project5.analysis.*;

@SuppressWarnings("nls")
public final class AVarlist extends PVarlist
{
    private TId _id_;
    private TColon _colon_;
    private PType _type_;
    private TSubset _subset_;
    private final LinkedList<PVarlisttail> _varlisttail_ = new LinkedList<PVarlisttail>();

    public AVarlist()
    {
        // Constructor
    }

    public AVarlist(
        @SuppressWarnings("hiding") TId _id_,
        @SuppressWarnings("hiding") TColon _colon_,
        @SuppressWarnings("hiding") PType _type_,
        @SuppressWarnings("hiding") TSubset _subset_,
        @SuppressWarnings("hiding") List<?> _varlisttail_)
    {
        // Constructor
        setId(_id_);

        setColon(_colon_);

        setType(_type_);

        setSubset(_subset_);

        setVarlisttail(_varlisttail_);

    }

    @Override
    public Object clone()
    {
        return new AVarlist(
            cloneNode(this._id_),
            cloneNode(this._colon_),
            cloneNode(this._type_),
            cloneNode(this._subset_),
            cloneList(this._varlisttail_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVarlist(this);
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

    public TSubset getSubset()
    {
        return this._subset_;
    }

    public void setSubset(TSubset node)
    {
        if(this._subset_ != null)
        {
            this._subset_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._subset_ = node;
    }

    public LinkedList<PVarlisttail> getVarlisttail()
    {
        return this._varlisttail_;
    }

    public void setVarlisttail(List<?> list)
    {
        for(PVarlisttail e : this._varlisttail_)
        {
            e.parent(null);
        }
        this._varlisttail_.clear();

        for(Object obj_e : list)
        {
            PVarlisttail e = (PVarlisttail) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._varlisttail_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._id_)
            + toString(this._colon_)
            + toString(this._type_)
            + toString(this._subset_)
            + toString(this._varlisttail_);
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

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._type_ == child)
        {
            this._type_ = null;
            return;
        }

        if(this._subset_ == child)
        {
            this._subset_ = null;
            return;
        }

        if(this._varlisttail_.remove(child))
        {
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

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._type_ == oldChild)
        {
            setType((PType) newChild);
            return;
        }

        if(this._subset_ == oldChild)
        {
            setSubset((TSubset) newChild);
            return;
        }

        for(ListIterator<PVarlisttail> i = this._varlisttail_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PVarlisttail) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
